package zyx.server.world.humanoids.npc;

import java.util.HashMap;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public abstract class BaseNpc<T extends Enum> extends HumanoidEntity implements IUpdateable
{
	private static final String TOSTRING_TEMPLATE = "%s(%s)";

	private HashMap<T, BaseNpcBehavior> behaviors;
	private BaseNpcBehavior requestedBehavior;
	private BaseNpcBehavior currentBehavior;

	private T currentState;

	public BaseNpc(NpcSetup setup)
	{
		super(setup.name, setup.gender);

		behaviors = new HashMap<>();
	}

	protected void addBehavior(BaseNpcBehavior<? extends BaseNpc, T, ?> behavior)
	{
		if (behaviors.containsKey(behavior.type))
		{
			System.err.println("Behavior already exists for " + behavior.type);
			System.err.println(behavior + " VS " + behaviors.get(behavior.type));
		}
		
		behaviors.put(behavior.type, behavior);
	}

	public void requestBehavior(T type, Object params)
	{
		requestedBehavior = behaviors.get(type);
		
		if (requestedBehavior != null)
		{
			currentState = type;
			requestedBehavior.setParams(params);
		}
		else
		{
			System.out.println("Behavior not found for type: " + type);
		}
	}

	public void requestBehavior(T type)
	{
		requestBehavior(type, null);
	}

	public T getCurrentState()
	{
		return currentState;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (requestedBehavior != null)
		{
			if (currentBehavior != null)
			{
				currentBehavior.exit();
			}
			
			currentBehavior = requestedBehavior;
			currentBehavior.enter();
			
			requestedBehavior = null;
		}
		
		if (currentBehavior != null)
		{
			currentBehavior.update(timestamp, elapsedTime);
		}
	}

	@Override
	public String toString()
	{
		String clazz = getClass().getSimpleName();
		return String.format(TOSTRING_TEMPLATE, clazz, name);
	}

	@Override
	public int getSize()
	{
		return 20;
	}
	
	@Override
	public boolean isRound()
	{
		return true;
	}
}
