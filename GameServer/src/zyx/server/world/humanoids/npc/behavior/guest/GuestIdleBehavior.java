package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;

public class GuestIdleBehavior extends BaseNpcBehavior<GuestBehaviorType>
{

	public GuestIdleBehavior(BaseNpc npc)
	{
		super(npc, GuestBehaviorType.IDLE);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}
}
