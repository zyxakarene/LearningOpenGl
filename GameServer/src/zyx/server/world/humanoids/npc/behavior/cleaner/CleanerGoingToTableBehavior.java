package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;
import zyx.server.world.interactable.common.CommonTable;

public class CleanerGoingToTableBehavior extends BaseNpcWalkingBehavior<Cleaner, CleanerBehaviorType, CommonTable>
{

	public CleanerGoingToTableBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GETTING_DIRTY_PLATE);
	}
	
	@Override
	protected void onEnter()
	{
		setTarget(params.x, params.y, params.z);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.clean(npc);
	}
}
