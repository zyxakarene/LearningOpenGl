package zyx.server.world.interactable.chef;

import zyx.server.world.humanoids.handheld.food.DishRecipeItem;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;

public class ChefFridge extends ChefItem
{
	private static final int PICKUP_TIME = 500;
	
	private DishRecipeItem recipe;
	
	public ChefFridge()
	{
		super(PICKUP_TIME);
	}
	
	@Override
	public void interactWith(Chef chef)
	{
		recipe = chef.getHeldAsRecipe();
		
		if (recipe != null)
		{
			chef.removeItem();
			startUsing(chef);
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		FoodItem ingredients = new FoodItem(recipe.dish);
		currentChef.pickupItem(ingredients);
		currentChef.requestBehavior(ChefBehaviorType.WALKING_TO_STOVE);
	}
}
