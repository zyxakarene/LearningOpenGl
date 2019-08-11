package zyx.server.world.humanoids.npc.behavior;

import org.lwjgl.util.vector.Vector3f;
import zyx.server.world.humanoids.npc.BaseNpc;

public abstract class BaseNpcWalkingBehavior<NPC extends BaseNpc, T extends Enum, P> extends BaseNpcBehavior<NPC, T, P>
{
	private static final float SPEED = 1f / 16f;
	private static final Vector3f HELPER = new Vector3f();
	
	private boolean walking;
	
	private Vector3f start;
	private Vector3f target;
	private Vector3f moveDir;
	
	private float timeToWalk;
	private int timeWalked;

	public BaseNpcWalkingBehavior(NPC npc, T type)
	{
		super(npc, type);
		
		start = new Vector3f();
		target = new Vector3f();
		moveDir = new Vector3f();
	}

	protected void setTarget(float x, float y, float z)
	{
		start.set(npc.x, npc.y, npc.z);
		target.set(x, y, z);
		
		Vector3f.sub(target, start, HELPER);
		HELPER.normalise(moveDir);
		
		float length = HELPER.length();
		timeToWalk = length / SPEED;
		timeWalked = 0;
		
		walking = true;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (walking)
		{
			timeWalked += elapsedTime;
			
			HELPER.x = start.x + (moveDir.x * SPEED * timeWalked);
			HELPER.y = start.y + (moveDir.y * SPEED * timeWalked);
			HELPER.z = start.z + (moveDir.z * SPEED * timeWalked);
			npc.updatePosition(HELPER.x, HELPER.y, HELPER.z);
			
			if (timeWalked >= timeToWalk)
			{
				walking = false;
				
				npc.updatePosition(target.x, target.y, target.z);
				
				System.out.println(npc + " arrived at destination");
				onArrivedAtDestination();
			}
		}
	}

	protected abstract void onArrivedAtDestination();
}
