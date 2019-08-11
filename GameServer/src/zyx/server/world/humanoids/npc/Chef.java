package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.handheld.food.DishRecipeItem;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public class Chef extends BaseNpc<ChefBehaviorType>
{

	public Chef(NpcSetup setup)
	{
		super(setup);
		
		lx = 0;
		ly = 0;
		lz = 0;
	}
	
	public FoodItem getHeldAsFood()
	{
		if (heldItem instanceof FoodItem)
		{
			return (FoodItem) heldItem;
		}
		
		return null;
	}
	
	public DishRecipeItem getHeldAsRecipe()
	{
		if (heldItem instanceof DishRecipeItem)
		{
			return (DishRecipeItem) heldItem;
		}
		
		return null;
	}
}
