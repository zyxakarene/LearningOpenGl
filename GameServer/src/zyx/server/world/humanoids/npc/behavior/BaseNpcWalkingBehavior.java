package zyx.server.world.humanoids.npc.behavior;

import org.lwjgl.util.vector.Vector3f;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.pathfanding.AStarPathFinder;

public abstract class BaseNpcWalkingBehavior<NPC extends BaseNpc, T extends Enum, P extends BaseInteractableItem<NPC>> extends BaseNpcBehavior<NPC, T, P>
{

	private static final float SPEED = 1f / 16f;
	protected static final Vector3f HELPER = new Vector3f();
	protected static final Vector3f HELPER_LOOK = new Vector3f();

	private boolean walking;
	private boolean looking;
	private boolean readyToLook;

	private Vector3f walkStart;
	private Vector3f walkTarget;
	private Vector3f walkDir;

	private Vector3f lookStart;
	private Vector3f lookTarget;
	private Vector3f lookDir;

	private float timeToWalk;
	private int timeWalked;

	private float timeToLook;
	private int timeLooked;

	private Vector3f pathEndLook;
	
	private AStarPathFinder pathFinder;

	public BaseNpcWalkingBehavior(NPC npc, T type)
	{
		super(npc, type);

		walkStart = new Vector3f();
		walkTarget = new Vector3f();
		walkDir = new Vector3f();

		lookStart = new Vector3f();
		lookTarget = new Vector3f();
		lookDir = new Vector3f();
		
		pathEndLook = new Vector3f();

		pathFinder = new AStarPathFinder();
	}

	@Override
	protected void onEnter()
	{
		params.getUsingPosition(HELPER, HELPER_LOOK);
		setTarget(HELPER, HELPER_LOOK);
	}
	
	protected void setTarget(Vector3f pos, Vector3f lookPos)
	{
		pathEndLook.set(lookPos);
		pathFinder.preparePath(npc.x, npc.y, npc.z, pos.x, pos.y, pos.z);
		walking = true;
		readyToLook = true;
		looking = false;

		onHitNode();
		
		npc.updateLook(walkTarget.x, walkTarget.y, walkTarget.z);
	}

	private void onHitNode()
	{
		looking = false;
		readyToLook = true;

		if (pathFinder.hasMoreNodes())
		{
			walkStart.set(npc.x, npc.y, npc.z);
			pathFinder.getCurrentTarget(walkTarget);

			calculateWalkData();
		}
		else
		{
			System.out.println(npc + " arrived at destination");

			walking = false;
			onArrivedAtDestination();
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (walking)
		{
			timeWalked += elapsedTime;

			HELPER.x = walkStart.x + (walkDir.x * SPEED * timeWalked);
			HELPER.y = walkStart.y + (walkDir.y * SPEED * timeWalked);
			HELPER.z = walkStart.z + (walkDir.z * SPEED * timeWalked);
			npc.updatePosition(HELPER.x, HELPER.y, HELPER.z);

			if (readyToLook && timeWalked / timeToWalk >= 0.85f)
			{
				if (pathFinder.hasNextNode())
				{
					pathFinder.getNextTarget(lookTarget);
				}
				else
				{
					HELPER.set(npc.x, npc.y, npc.z);
					Vector3f.sub(walkTarget, HELPER, HELPER);
					HELPER.normalise();
					
					lookTarget.x = npc.x + (HELPER.x * 100); 
					lookTarget.y = npc.y + (HELPER.y * 100); 
					lookTarget.z = npc.z + (HELPER.z * 100); 
					lookTarget.set(pathEndLook);
				}
				
				lookStart.set(npc.lx, npc.ly, npc.lz);
				readyToLook = false;
				looking = true;

				calculateLookData();
			}

			if (looking)
			{
				timeLooked += elapsedTime;
				HELPER.x = lookStart.x + (lookDir.x * SPEED * timeLooked);
				HELPER.y = lookStart.y + (lookDir.y * SPEED * timeLooked);
				HELPER.z = lookStart.z + (lookDir.z * SPEED * timeLooked);
				npc.updateLook(HELPER.x, HELPER.y, HELPER.z);
			}

			if (timeWalked >= timeToWalk)
			{
				pathFinder.onHitNode();
				npc.updatePosition(walkTarget.x, walkTarget.y, walkTarget.z);

				onHitNode();
			}

			if (looking && timeLooked >= timeToLook)
			{
				npc.updateLook(lookTarget.x, lookTarget.y, lookTarget.z);
			}
		}
	}

	protected abstract void onArrivedAtDestination();

	private void calculateWalkData()
	{
		Vector3f.sub(walkTarget, walkStart, HELPER);
		HELPER.normalise(walkDir);

		float length = HELPER.length();
		timeToWalk = length / SPEED;
		timeWalked = 0;

		npc.updateLook(walkTarget.x, walkTarget.y, walkTarget.z);
	}

	private void calculateLookData()
	{
		Vector3f.sub(lookTarget, lookStart, HELPER);
		HELPER.normalise(lookDir);
		
		if (lookDir.x != lookDir.x)
		{
			System.out.println("Nan");
		}

		float length = HELPER.length();
		timeToLook = length / SPEED;
		timeLooked = 0;
	}
}
