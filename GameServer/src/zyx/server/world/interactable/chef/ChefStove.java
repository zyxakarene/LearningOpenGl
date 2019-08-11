package zyx.server.world.interactable.chef;

import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;

public class ChefStove extends ChefItem
{
	private static final int COOK_TIME = 50;

	private FoodItem foodItem;

	public ChefStove()
	{
		super(COOK_TIME);
	}

	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			foodItem = chef.getHeldAsFood();

			if (foodItem != null)
			{
				foodItem.process();
				chef.removeItem();
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		foodItem.process();
		currentUser.pickupItem(foodItem);
		
		currentUser.requestBehavior(ChefBehaviorType.FINDING_TABLE);
	}

}
