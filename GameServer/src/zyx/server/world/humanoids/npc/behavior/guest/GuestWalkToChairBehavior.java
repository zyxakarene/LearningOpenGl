package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.GuestChair;

public class GuestWalkToChairBehavior extends GuestWalkingBehavior<GuestChair>
{

	public GuestWalkToChairBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WALKING_TO_CHAIR);
	}

	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
