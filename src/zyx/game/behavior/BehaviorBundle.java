package zyx.game.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.game.components.GameObject;
import zyx.utils.interfaces.IUpdateable;

public class BehaviorBundle implements IUpdateable
{
	private final HashMap<BehaviorType, Behavior> behaviorMap;
	private final ArrayList<Behavior> behaviors;
	private final GameObject gameObject;

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
	
}
