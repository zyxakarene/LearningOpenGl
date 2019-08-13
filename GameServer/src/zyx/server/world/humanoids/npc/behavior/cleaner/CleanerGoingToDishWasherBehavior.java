package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.interactable.cleaner.DishWasher;

public class CleanerGoingToDishWasherBehavior extends CleanerWalkingBehavior<DishWasher>
{

	public CleanerGoingToDishWasherBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GOING_TO_DISH_WASHER);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		npc.requestBehavior(CleanerBehaviorType.WAITING_FOR_DISH_WASHER);
	}
}
