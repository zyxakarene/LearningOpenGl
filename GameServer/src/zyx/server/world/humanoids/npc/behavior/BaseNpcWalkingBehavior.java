package zyx.server.world.humanoids.npc.behavior;

import org.lwjgl.util.vector.Vector3f;
import zyx.server.world.humanoids.npc.BaseNpc;

public abstract class BaseNpcWalkingBehavior<NPC extends BaseNpc, T extends Enum, P> extends BaseNpcBehavior<NPC, T, P>
{

	private Vector3f target;
	
	private float distanceToWalk;
	private int timeToWalk;

	public BaseNpcWalkingBehavior(NPC npc, T type)
	{
		super(npc, type);
		
		target = new Vector3f();
	}

	protected void setTarget(float x, float y, float z)
	{
		target.set(x, y, z);
		timeToWalk = 2000;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (timeToWalk > 0)
		{
			timeToWalk -= elapsedTime;
			if (timeToWalk <= 0)
			{
				System.out.println(npc + " arrived at destination");
				onArrivedAtDestination();
			}
		}
	}

	protected abstract void onArrivedAtDestination();
}
