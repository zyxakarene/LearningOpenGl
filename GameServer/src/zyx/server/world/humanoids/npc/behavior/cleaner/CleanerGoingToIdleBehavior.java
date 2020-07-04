package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.interactable.cleaner.DishWasher;

public class CleanerGoingToIdleBehavior extends CleanerWalkingBehavior<DishWasher>
{

	public CleanerGoingToIdleBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GOING_TO_IDLE);
	}

	@Override
	protected void onEnter()
	{
		HELPER.set(150, 250, 0);
		HELPER_LOOK.set(140, 250, 0);
		setTarget(HELPER, HELPER_LOOK);
	}

	@Override
	protected void onArrivedAtDestination()
	{
		npc.requestBehavior(CleanerBehaviorType.IDLE);
	}
}
