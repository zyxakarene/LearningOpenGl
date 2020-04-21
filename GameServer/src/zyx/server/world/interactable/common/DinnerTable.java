package zyx.server.world.interactable.common;

import java.awt.Color;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.ItemService;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;
import zyx.server.controller.services.NpcService;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.guests.Chair;

public class DinnerTable extends CommonTable<Guest>
{

	private static final int MAX_GUEST_TABLE_ITEM_COUNT = 5;

	public boolean hasGottenBill;

	public final Chair[] chairs;
	public final int chairCount;

	public DinnerTable(Chair[] connectedChairs)
	{
		super(MAX_GUEST_TABLE_ITEM_COUNT, FurnitureType.DINNER_TABLE);
		chairs = connectedChairs;
		chairCount = connectedChairs.length;
	}

	@Override
	protected void onPlayerGive(Player player)
	{
		HandheldItem itemToGive = player.heldItem();
		if (itemToGive == null)
		{
			return;
		}
		
		TableGiveResponse addedResponse = tryAddItem(itemToGive);

		switch (addedResponse)
		{
			case SUCCESS:
			{
				if (itemToGive.type == HandheldItemType.BILL)
				{
					giveBill((BillItem) itemToGive);
				}
				else if (itemToGive.type == HandheldItemType.FOOD)
				{
					giveFood((FoodItem) itemToGive);
				}	player.removeItemSilent();
				break;
			}
			case NO_ORDERS_GIVEN:
			{
				NpcService.reportNoOrdersTo(player, currentUser);
				break;
			}
			case NO_ROOM:
			{
				NpcService.reportTableFullTo(player, currentUser);
				break;
			}
		}
	}

	@Override
	protected TableGiveResponse getReasonForNotAccepting(HandheldItem item)
	{
		return TableGiveResponse.NO_ORDERS_GIVEN;
	}
	
	@Override
	protected boolean canAcceptItem(HandheldItem item)
	{
		if (item.type == HandheldItemType.FOOD)
		{
			for (Chair chair : chairs)
			{
				Guest guestInChair = chair.getCurrentGuest();
				if (guestInChair != null && !guestInChair.hasOrdered)
				{
					return false;
				}
			}
		}
		
		return true;
	}

	private void giveBill(BillItem bill)
	{
		if (inUse && !hasGottenBill)
		{
			hasGottenBill = true;

			for (Chair chair : chairs)
			{
				Guest guestInChair = chair.getCurrentGuest();
				if (guestInChair != null)
				{
					guestInChair.group.bill = bill;
					guestInChair.hasBill = true;
				}
			}
		}
	}

	public boolean canReceiveBill()
	{
		boolean allGuestsArrived = true;
		for (Chair chair : chairs)
		{
			allGuestsArrived = allGuestsArrived && chair.isCurrentGuestSitting();
		}

		return !hasGottenBill && allGuestsArrived;
	}

	private void giveFood(FoodItem food)
	{
		//Try to find the correct guest
		for (Chair chair : chairs)
		{
			Guest guestInChair = chair.getCurrentGuest();
			if (guestInChair != null && !guestInChair.hasEaten && guestInChair.dishRequest == food.dish && guestInChair.hasOrdered)
			{
				if (food.isEdible())
				{
					//Guest wants the food and haven't eaten yet
					guestInChair.serveFood(food);
					return;
				}
			}
		}

		//Nobody wanted the food, force someone to take it!
		for (Chair chair : chairs)
		{
			Guest guestInChair = chair.getCurrentGuest();
			if (guestInChair != null && !guestInChair.hasEaten && guestInChair.hasOrdered)
			{
				if (food.isEdible())
				{
					//Guest haven't eaten yet, and might not want the food
					guestInChair.serveFood(food);
					return;
				}
			}
		}
	}

	@Override
	public Color getColor()
	{
		return new Color(255, 160, 50);
	}

	public void removeBill(int billID)
	{
		hasGottenBill = false;
		HandheldItem bill = removeItemById(billID);
		if (bill != null)
		{
			ItemService.destroyItem(bill);
		}
	}
}
