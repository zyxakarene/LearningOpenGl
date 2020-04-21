package zyx.server.world.interactable.chef;

import java.awt.Color;
import zyx.game.vo.FoodState;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.ItemService;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.common.useable.UseableItem;

public class Fridge extends UseableItem<Chef>
{
	private static final int PICKUP_TIME = 800;
	
	private FoodItem recipe;
	
	public Fridge()
	{
		super(PICKUP_TIME, FurnitureType.FRIDGE);
	}
	
	@Override
	public void interactWith(Chef chef)
	{
		if (canUse(chef))
		{
			FoodItem heldItem = chef.getHeldAsFood();

			if (heldItem != null && heldItem.state == FoodState.RECIPE)
			{
				recipe = heldItem;
				recipe.process();
				
				chef.removeItemSilent();
				startUsing(chef);
			}
		}
	}

	@Override
	protected void onUsingCompleted()
	{
		currentUser.pickupItemSilent(recipe);
		currentUser.requestBehavior(ChefBehaviorType.FINDING_STOVE);
		
		ItemService.createFood(recipe, currentUser.id);
	}

	@Override
	public Color getColor()
	{
		return Color.PINK;
	}
}
