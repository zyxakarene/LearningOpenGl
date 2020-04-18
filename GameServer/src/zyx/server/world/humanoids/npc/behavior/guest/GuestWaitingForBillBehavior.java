package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.controller.services.NpcService;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.Chair;
import zyx.server.world.wallet.MoneyJar;
import zyx.server.world.wallet.TipCalculator;

public class GuestWaitingForBillBehavior extends GuestBehavior<Object>
{

	private Chair[] groupChairs;
	private boolean hasGottenBill;
	private int delayTimer;

	public GuestWaitingForBillBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WAITING_FOR_BILL);

		delayTimer = 2000;
	}

	@Override
	protected void onEnter()
	{
		groupChairs = npc.group.table.chairs;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (npc.isLeader && npc.group.table.hasGottenBill)
		{
			hasGottenBill = true;
		}

		if (hasGottenBill)
		{
			delayTimer -= elapsedTime;

			if (delayTimer <= 0)
			{
				boolean isAllReady = true;
				for (Chair chair : groupChairs)
				{
					if (chair != null && chair.currentUser != null)
					{
						Guest friend = chair.currentUser;
						if (friend.hasBill && friend.getCurrentState() != GuestBehaviorType.WAITING_FOR_BILL)
						{
							//Someone is still not done eating yet
							isAllReady = false;
							break;
						}
					}
				}

				if (isAllReady)
				{
					finishVisit();
				}
			}
		}
	}

	private void finishVisit()
	{
		npc.group.table.makeAvailible();
		npc.group.table.removeBill(npc.group.bill.id);

		int[] guestIds = new int[npc.group.size];
		int[] payAmounts = new int[npc.group.size];
		int index = 0;

		for (Chair chair : groupChairs)
		{
			if (chair != null && chair.currentUser != null)
			{
				Guest friend = chair.currentUser;
				friend.requestBehavior(GuestBehaviorType.WALKING_OUT, items.exitPoint);

				chair.makeAvailible();

				int payAmount = 0;
				if (friend.servedDish != null)
				{
					if (friend.gotRightDish)
					{
						payAmount = friend.servedDish.sellValue;
					}
					else
					{
						payAmount = friend.servedDish.sellValue / 2;
					}
				}
				if (payAmount > friend.dishRequest.sellValue)
				{
					payAmount = friend.dishRequest.sellValue;
				}

				int tipAmount = TipCalculator.calculateTip(friend);

				MoneyJar.getInstance().payEarning(payAmount);
				MoneyJar.getInstance().addTip(tipAmount);

				guestIds[index] = friend.id;
				payAmounts[index] = payAmount + tipAmount;
				index++;
			}
		}
		
		NpcService.guestPays(guestIds, payAmounts);
	}
}
