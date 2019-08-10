package zyx.server.world.humanoids.npc.behavior.chef;

import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;

public class ChefIdleBehavior extends BaseNpcBehavior<ChefBehaviorType>
{

	public ChefIdleBehavior(BaseNpc npc)
	{
		super(npc, ChefBehaviorType.IDLE);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		npc.x = (float) (Math.sin(timestamp * 0.001) * 10);
		npc.y = (float) (Math.cos(timestamp * 0.001) * 10);
		npc.updatedPosition = true;
	}
}
