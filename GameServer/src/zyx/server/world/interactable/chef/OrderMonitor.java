package zyx.server.world.interactable.chef;

import java.util.LinkedList;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.food.DishRecipeItem;
import zyx.server.world.humanoids.handheld.food.DishType;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;

public class OrderMonitor extends ChefItem
{
	private static final int LOOK_TIME = 800;
	
	private LinkedList<DishRecipeItem> queue;

	public OrderMonitor()
	{
		super(LOOK_TIME);
		
		queue = new LinkedList<>();
	}
	
	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			HandheldItem heldItem = chef.heldItem();
			if (heldItem == null && !queue.isEmpty())
			{
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		DishRecipeItem recipe = queue.removeFirst();
		currentUser.pickupItem(recipe);
		currentUser.requestBehavior(ChefBehaviorType.FINDING_FRIDGE);
	}
	
	public void addDish(DishType dish)
	{
		DishRecipeItem recipe = new DishRecipeItem(dish);
		queue.addLast(recipe);
	}
	
	public boolean hasOrders()
	{
		return !queue.isEmpty();
	}

}
