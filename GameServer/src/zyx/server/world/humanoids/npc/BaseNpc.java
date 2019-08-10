package zyx.server.world.humanoids.npc;

import java.util.HashMap;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public abstract class BaseNpc<T extends Enum> extends HumanoidEntity implements IUpdateable
{
	private HashMap<T, BaseNpcBehavior> behaviors;
	private BaseNpcBehavior currentBehavior;
	
	private T currentState;
	
	public BaseNpc(NpcSetup setup)
	{
		super(setup.name, setup.gender);
		
		behaviors = new HashMap<>();
	}
	
	protected void addBehavior(BaseNpcBehavior<T> behavior)
	{
		behaviors.put(behavior.type, behavior);
	}
	
	public void requestBehavior(T type)
	{
		currentBehavior = behaviors.get(type);
	}

	public T getCurrentState()
	{
		return currentState;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (currentBehavior != null)
		{
			currentBehavior.update(timestamp, elapsedTime);
		}
	}
}
