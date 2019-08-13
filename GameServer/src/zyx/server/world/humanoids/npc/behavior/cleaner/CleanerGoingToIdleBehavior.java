package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;

public class CleanerGoingToIdleBehavior extends BaseNpcWalkingBehavior<Cleaner, CleanerBehaviorType, Object>
{

	public CleanerGoingToIdleBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GOING_TO_IDLE);
	}

	@Override
	protected void onEnter()
	{
		setTarget(150, 250, 0);
	}

	@Override
	protected void onArrivedAtDestination()
	{
		npc.requestBehavior(CleanerBehaviorType.IDLE);
	}
}
