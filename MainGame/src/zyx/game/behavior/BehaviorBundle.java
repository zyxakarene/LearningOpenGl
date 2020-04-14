package zyx.game.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.game.components.GameObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class BehaviorBundle implements IUpdateable, IDisposeable
{
	private HashMap<BehaviorType, Behavior> behaviorMap;
	private ArrayList<Behavior> behaviors;
	private GameObject gameObject;

	public BehaviorBundle(GameObject gameObject)
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
		for (Behavior behavior : behaviors)
		{
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

	public int count()
	{
		if (behaviors != null)
		{
			return behaviors.size();
		}
		
		return 0;
	}
	
	@Override
	public void dispose()
	{
		for (Behavior behavior : behaviors)
		{
			behavior.dispose();
		}
		
		behaviors.clear();
		behaviorMap.clear();
		
		behaviors = null;
		gameObject = null;
		behaviorMap = null;
	}
}
