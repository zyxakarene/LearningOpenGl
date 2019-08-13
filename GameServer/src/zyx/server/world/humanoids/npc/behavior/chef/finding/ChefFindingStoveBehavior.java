package zyx.server.world.humanoids.npc.behavior.chef.finding;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.chef.Stove;

public class ChefFindingStoveBehavior extends BaseNpcBehavior<Chef, ChefBehaviorType, Object>
{
	private Stove[] stoves;
	
	public ChefFindingStoveBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.FINDING_STOVE);
		
		stoves = items.stoves;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (Stove stove : stoves)
		{
			if (!stove.inUse)
			{
				stove.claimOwnership(npc);
				npc.requestBehavior(ChefBehaviorType.COOKING_FOOD_STOVE, stove);
			}
		}
	}
}
