package zyx.server.world.interactable.chef;

import java.awt.Color;
import zyx.server.world.humanoids.handheld.food.DishRecipeItem;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.common.useable.UseableItem;

public class Fridge extends UseableItem<Chef>
{
	private static final int PICKUP_TIME = 800;
	
	private DishRecipeItem recipe;
	
	public Fridge()
	{
		super(PICKUP_TIME);
	}
	
	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			recipe = chef.getHeldAsRecipe();

			if (recipe != null)
			{
				chef.removeItem(true);
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		FoodItem ingredients = new FoodItem(recipe.dish);
		currentUser.pickupItem(ingredients);
		currentUser.requestBehavior(ChefBehaviorType.FINDING_STOVE);
	}

	@Override
	public Color getColor()
	{
		return Color.PINK;
	}
}
