package zyx.server.world.humanoids.npc.behavior.chef.using;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.behavior.chef.ChefWalkingBehavior;
import zyx.server.world.interactable.common.FoodTable;

public class ChefServingFoodBehavior extends ChefWalkingBehavior<FoodTable>
{

	public ChefServingFoodBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.SERVING_FOOD_TABLE);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
