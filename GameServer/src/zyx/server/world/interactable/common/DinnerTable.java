package zyx.server.world.interactable.common;

import java.awt.Color;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemType;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.guests.GuestChair;

public class DinnerTable extends CommonTable<Guest>
{

	private static final int MAX_GUEST_TABLE_ITEM_COUNT = 5;

	public boolean hasGottenBill;
	
	public final GuestChair[] chairs;
	public final int chairCount;

	public DinnerTable(GuestChair[] connectedChairs)
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

		if (wasGiven)
		{
			if (itemToGive.type == HandheldItemType.BILL)
			{
				giveBill();
			}
			else if (itemToGive.type == HandheldItemType.FOOD)
			{
				giveFood((FoodItem) itemToGive);
			}
			
			player.removeItem();
		}
	}

	private void giveBill()
	{
		hasGottenBill = true;
		
		for (GuestChair chair : chairs)
		{
			Guest guestInChair = chair.getCurrentGuest();
			if (guestInChair != null)
			{
				guestInChair.hasBill = true;
			}
		}
	}

	private void giveFood(FoodItem food)
	{
		//Try to find the correct guest
		for (GuestChair chair : chairs)
		{
			Guest guestInChair = chair.getCurrentGuest();
			if (guestInChair != null && !guestInChair.hasEaten && guestInChair.dishRequest == food.dish)
			{
				//Guest wants the food and haven't eaten yet
				guestInChair.serveFood(food);
				return;
			}
		}
		
		//Nobody wanted the food, force someone to take it!
		for (GuestChair chair : chairs)
		{
			Guest guestInChair = chair.getCurrentGuest();
			if (guestInChair != null && !guestInChair.hasEaten)
			{
				//Guest haven't eaten yet, and might not want the food
				guestInChair.serveFood(food);
				return;
			}
		}
	}
	
	@Override
	public Color getColor()
	{
		return new Color(255, 160, 50);
	}
}
