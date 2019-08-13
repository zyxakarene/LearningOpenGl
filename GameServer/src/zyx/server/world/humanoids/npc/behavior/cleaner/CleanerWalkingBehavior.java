package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;
import zyx.server.world.interactable.BaseInteractableItem;


public abstract class CleanerWalkingBehavior<P extends BaseInteractableItem<Cleaner>> extends BaseNpcWalkingBehavior<Cleaner, CleanerBehaviorType, P>
{

	public CleanerWalkingBehavior(Cleaner npc, CleanerBehaviorType type)
	{
		super(npc, type);
	}

	@Override
	protected void onEnter()
	{
		setTarget(params.x, params.y, params.z);
	}
}
