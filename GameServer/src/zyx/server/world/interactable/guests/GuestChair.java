package zyx.server.world.interactable.guests;

import zyx.server.world.humanoids.handheld.guests.GuestOrder;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.common.player.PlayerInteractable;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.player.PlayerInteraction;

public class GuestChair extends GuestItem implements PlayerInteractable
{
	private Guest currentGuest;
	private DinnerTable table;
	
	public GuestChair(DinnerTable table)
	{
		this.table = table;
	}

	@Override
	public void interactWith(Guest guest)
	{
		if (canUse(guest))
		{
			currentGuest = guest;
			inUse = true;

			currentGuest.requestBehavior(GuestBehaviorType.WAITING_TO_ORDER);
		}
	}

	public Guest getCurrentGuest()
	{
		return currentGuest;
	}
	
	@Override
	public void interactWith(Player player, PlayerInteraction interaction)
	{
		if (interaction.isTake() && currentGuest != null)
		{
			boolean canHold = player.canHoldItem();
			GuestOrder currentOrders = player.getHeldAsOrders();

			if (canHold || currentOrders.isMatchingTable(table))
			{
				if (currentOrders == null)
				{
					currentOrders = new GuestOrder(table);
				}

				currentOrders.addDish(currentGuest.dishRequest);
				currentGuest.requestBehavior(GuestBehaviorType.WAITING_FOR_FOOD);
			}
		}
	}
}
