package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.ExitPoint;

public class GuestWalkingOutBehavior extends GuestWalkingBehavior<ExitPoint>
{

	public GuestWalkingOutBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WALKING_OUT);
	}

	@Override
	protected void onArrivedAtDestination()
	{
		params.interactWith(npc);
	}
}
