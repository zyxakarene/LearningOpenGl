package zyx.server.world.humanoids.npc;

import java.awt.Color;
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
	public FoodItem servedFood;

	public boolean isLeader;
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
		isLeader = false;
	}

	public void serveFood(FoodItem food)
	{
		servedFood = food;
		servedFood.inUse = true;
		
		servedDish = food.dish;
		hasEaten = true;

		gotRightDish = (servedDish == dishRequest);
		if (!gotRightDish)
		{
			//Get angry!
		}
	}
	
	public void stopEating()
	{
		if (servedFood != null)
		{
			servedFood.process();
			servedFood.inUse = false;
			servedFood = null;
		}
	}

	@Override
	public boolean wantsToHold(HandheldItem item)
	{
		//Guests do not want to take bills.. For now??
		return (item instanceof BillItem) == false;
	}

	@Override
	public Color getColor()
	{
		return Color.RED;
	}
}
