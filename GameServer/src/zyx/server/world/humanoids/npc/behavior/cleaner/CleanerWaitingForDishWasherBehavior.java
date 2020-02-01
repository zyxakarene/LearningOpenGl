package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.interactable.cleaner.DishWasher;

public class CleanerWaitingForDishWasherBehavior extends BaseNpcBehavior<Cleaner, CleanerBehaviorType, Object>
{

	private final DishWasher dishWasher;
	
	public CleanerWaitingForDishWasherBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.WAITING_FOR_DISH_WASHER);
		
		dishWasher = items.dishWasher;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (!dishWasher.inUse)
		{
			dishWasher.claimOwnership(npc);
			dishWasher.interactWith(npc);
		}
	}
}
