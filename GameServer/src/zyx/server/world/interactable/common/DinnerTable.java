package zyx.server.world.interactable.common;

import java.awt.Color;
import zyx.server.controller.services.ItemService;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;
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
		super(MAX_GUEST_TABLE_ITEM_COUNT);
		chairs = connectedChairs;
		chairCount = connectedChairs.length;
	}

	@Override
	protected void onPlayerGive(Player player)
	{
		HandheldItem itemToGive = player.heldItem();

		boolean wasGiven = itemToGive != null && tryAddItem(itemToGive);

		if (wasGiven && itemToGive != null)
		{
			if (itemToGive.type == HandheldItemType.BILL)
			{
				giveBill((BillItem) itemToGive);
			}
			else if (itemToGive.type == HandheldItemType.FOOD)
			{
				giveFood((FoodItem) itemToGive);
			}

			player.removeItemSilent();
		}
	}

	private void giveBill(BillItem bill)
	{
		if (inUse && !hasGottenBill)
		{
			hasGottenBill = true;

			ItemService.createBill(bill, id);
			
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
			if (guestInChair != null && !guestInChair.hasEaten && guestInChair.dishRequest == food.dish)
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
			if (guestInChair != null && !guestInChair.hasEaten)
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
