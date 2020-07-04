package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.interactable.common.CommonTable;

public class CleanerGoingToTableBehavior extends CleanerWalkingBehavior<CommonTable<Cleaner>>
{

	public CleanerGoingToTableBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GETTING_DIRTY_PLATE_TABLE);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		params.clean(npc);
	}
}
