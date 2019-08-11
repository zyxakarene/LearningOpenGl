package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.food.DishType;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public class Guest extends BaseNpc<GuestBehaviorType>
{

	public final DishType dishRequest;
	public DishType servedDish;
	
	public boolean hasEaten;
	public boolean hasBill;
	public boolean gotRightDish;
	public GuestGroup group;
	
	public Guest(NpcSetup setup)
	{
		super(setup);
		
		dishRequest = DishType.getRandomDish();
		
		hasEaten = false;
		hasBill = false;
	}

	@Override
	public void pickupItem(HandheldItem item)
	{
		super.pickupItem(item);
		
		if (item instanceof FoodItem)
		{
			FoodItem food = (FoodItem) item;
			servedDish = food.dish;
			hasEaten = true;
			
			gotRightDish = (servedDish == dishRequest);
			if (!gotRightDish)
			{
				//Get angry!
			}
		}
	}
	
	@Override
	public boolean wantsToHold(HandheldItem item)
	{
		//Guests do not want to take bills.. For now??
		return (item instanceof BillItem) == false;
	}
}
