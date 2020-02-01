package zyx.server.world.humanoids.npc.behavior.chef.using;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.behavior.chef.ChefWalkingBehavior;
import zyx.server.world.interactable.chef.Monitor;

public class ChefGettingRecipeBehavior extends ChefWalkingBehavior<Monitor>
{

	public ChefGettingRecipeBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.GETTING_RECIPE_MONITOR);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
