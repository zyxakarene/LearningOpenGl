package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;

public class GuestWaitingForOrderBehavior extends GuestBehavior<Object>
{

	public GuestWaitingForOrderBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WAITING_TO_ORDER);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}
}
