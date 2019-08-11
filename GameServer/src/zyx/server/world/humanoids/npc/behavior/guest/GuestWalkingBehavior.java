package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;
import zyx.server.world.interactable.BaseInteractableItem;


public abstract class GuestWalkingBehavior<P extends BaseInteractableItem<Guest>> extends BaseNpcWalkingBehavior<Guest, GuestBehaviorType, P>
{

	public GuestWalkingBehavior(Guest npc, GuestBehaviorType type)
	{
		super(npc, type);
	}

	@Override
	protected void onEnter()
	{
		setTarget(params.x, params.y, params.z);
	}
}
