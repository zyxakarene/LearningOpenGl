package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.Chair;

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

		for (Chair chair : groupChairs)
		{
			if (chair != null && chair.currentUser != null)
			{
				Guest friend = chair.currentUser;
				friend.requestBehavior(GuestBehaviorType.WALKING_OUT, items.exitPoint);

				chair.makeAvailible();
			}
		}
	}
}
