package zyx.server.world.humanoids.npc.behavior.chef.using;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.behavior.chef.ChefWalkingBehavior;
import zyx.server.world.interactable.chef.ChefStove;

public class ChefCookingFoodBehavior extends ChefWalkingBehavior<ChefStove>
{

	public ChefCookingFoodBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.COOKING_FOOD_STOVE);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
