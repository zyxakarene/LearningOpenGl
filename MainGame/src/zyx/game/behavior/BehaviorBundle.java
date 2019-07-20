package zyx.game.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class BehaviorBundle implements IUpdateable, IDisposeable
{
	private HashMap<BehaviorType, Behavior> behaviorMap;
	private ArrayList<Behavior> behaviors;
	private WorldObject gameObject;

	public BehaviorBundle(WorldObject gameObject)
	{
		behaviors = new ArrayList<>();
		behaviorMap = new HashMap<>();
		
		this.gameObject = gameObject;
	}
	
	public void addBehavior(Behavior behavior)
	{
		behaviors.add(behavior);
		behaviorMap.put(behavior.type, behavior);
		
		behavior.setGameObject(gameObject);
		behavior.initialize();
	}

	public void removeBehavior(BehaviorType type)
	{
		Behavior behavior = behaviorMap.get(type);
	
		if (behavior != null)
		{
			behaviorMap.remove(type);
			behaviors.remove(behavior);
			
			behavior.dispose();
		}
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		int len = behaviors.size();
		Behavior behavior;
		for (int i = 0; i < len; i++)
		{
			behavior = behaviors.get(i);
			
			if(behavior.active)
			{
				behavior.update(timestamp, elapsedTime);
			}
		}
	}

	public Behavior getBehaviorById(BehaviorType id)
	{
		return behaviorMap.get(id);
	}

	@Override
	public void dispose()
	{
		int len = behaviors.size();
		Behavior behavior;
		for (int i = 0; i < len; i++)
		{
			behavior = behaviors.get(i);
			behavior.dispose();
		}
		
		behaviors.clear();
		behaviorMap.clear();
		
		behaviors = null;
		gameObject = null;
		behaviorMap = null;
	}
}
