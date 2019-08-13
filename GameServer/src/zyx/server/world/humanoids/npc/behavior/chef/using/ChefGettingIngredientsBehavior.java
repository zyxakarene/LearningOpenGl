package zyx.server.world.humanoids.npc.behavior.chef.using;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.behavior.chef.ChefWalkingBehavior;
import zyx.server.world.interactable.chef.Fridge;

public class ChefGettingIngredientsBehavior extends ChefWalkingBehavior<Fridge>
{

	public ChefGettingIngredientsBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.GETTING_INGREDIENTS_FRIDGE);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
