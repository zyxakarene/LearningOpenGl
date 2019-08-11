package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.GuestExitPoint;

public class GuestWalkingOutBehavior extends GuestWalkingBehavior<GuestExitPoint>
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
