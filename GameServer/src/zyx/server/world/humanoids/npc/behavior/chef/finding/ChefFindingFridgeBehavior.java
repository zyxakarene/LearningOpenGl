package zyx.server.world.humanoids.npc.behavior.chef.finding;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.chef.ChefFridge;

public class ChefFindingFridgeBehavior extends BaseNpcBehavior<Chef, ChefBehaviorType, Object>
{
	private ChefFridge[] fridges;
	
	public ChefFindingFridgeBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.FINDING_FRIDGE);
		
		fridges = items.fridges;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (ChefFridge fridge : fridges)
		{
			if (!fridge.inUse)
			{
				fridge.claimOwnership(npc);
				npc.requestBehavior(ChefBehaviorType.GETTING_INGREDIENTS_FRIDGE, fridge);
			}
		}
	}
}
