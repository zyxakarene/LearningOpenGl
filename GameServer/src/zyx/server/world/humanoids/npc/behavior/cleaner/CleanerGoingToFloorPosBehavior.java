package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;
import zyx.server.world.interactable.floor.FloorItem;

public class CleanerGoingToFloorPosBehavior extends BaseNpcWalkingBehavior<Cleaner, CleanerBehaviorType, FloorItem>
{
	
	public CleanerGoingToFloorPosBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.GETTING_DIRTY_PLATE_FLOOR);
	}
	
	@Override
	protected void onEnter()
	{
		setTarget(params.x, params.y, params.z);
	}
	
	@Override
	protected void onArrivedAtDestination()
	{
		items.floor.clean(npc);
	}
}
