package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.Chair;

public class GuestWalkToChairBehavior extends GuestWalkingBehavior<Chair>
{

	public GuestWalkToChairBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WALKING_TO_CHAIR);
	}

	@Override
	protected void onEnter()
	{
		super.onEnter();
	}

	
	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
