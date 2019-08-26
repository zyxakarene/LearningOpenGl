package zyx.server.world.interactable.guests;

import java.awt.Color;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.ItemService;
import zyx.server.world.humanoids.handheld.guests.GuestOrder;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.common.player.IPlayerInteractable;

public class Chair extends GuestItem implements IPlayerInteractable
{

	private DinnerTable table;
	private boolean guestSitting;

	public Chair()
	{
		super(FurnitureType.CHAIR);
	}

	public void linkToTable(DinnerTable table)
	{
		this.table = table;
	}

	@Override
	public void makeAvailible()
	{
		super.makeAvailible();
		guestSitting = false;
		currentUser = null;
	}

	@Override
	public void interactWith(Guest guest)
	{
		if (canUse(guest))
		{
			guestSitting = true;
			currentUser.requestBehavior(GuestBehaviorType.WAITING_TO_ORDER);
		}
	}

	public boolean isCurrentGuestSitting()
	{
		return currentUser != null && guestSitting;
	}

	public Guest getCurrentGuest()
	{
		return currentUser;
	}

	@Override
	public void interactWith(Player player, PlayerInteraction interaction)
	{
		if (interaction.isTake() && guestSitting && !table.hasGottenBill && !currentUser.hasEaten)
		{
			boolean canHold = player.canHoldItem();
			GuestOrder currentOrders = player.getHeldAsOrders();

			if (canHold || (currentOrders != null && currentOrders.isMatchingTable(table)))
			{
				boolean isNew = false;
				if (currentOrders == null)
				{
					isNew = true;
					currentOrders = new GuestOrder(table);
					player.pickupItemSilent(currentOrders);
				}

				System.out.println(currentUser + " ordered the dish: " + currentUser.dishRequest);

				currentOrders.addDish(currentUser.dishRequest);
				currentUser.requestBehavior(GuestBehaviorType.WAITING_FOR_FOOD);

				if (isNew)
				{
					ItemService.createOrders(currentOrders, currentUser.dishRequest, player.id);
				}
				else
				{
					ItemService.addToOrders(currentOrders, currentUser.dishRequest);

				}
			}
		}
	}

	@Override
	public Color getColor()
	{
		return new Color(255, 235, 150);
	}
}
