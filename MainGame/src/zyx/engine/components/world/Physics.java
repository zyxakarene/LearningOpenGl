package zyx.engine.components.world;

import java.util.ArrayList;
import zyx.engine.components.world.physics.CollisionChecker;
import zyx.utils.interfaces.IUpdateable;

public final class Physics implements IUpdateable
{

	private ArrayList<Collider> colliders;
	
	Physics()
	{
		colliders = new ArrayList<>();
	}
	
	void addCollider(Collider collider)
	{
		if (colliders.contains(collider) == false)
		{
			colliders.add(collider);
		}
	}
	
	void removeCollider(Collider collider)
	{
		colliders.remove(collider);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		int len = colliders.size();
		for (int i = 0; i < len; i++)
		{
			colliders.get(i).velocity.z -= 1;
			colliders.get(i).update(timestamp, elapsedTime);
		}
		
		Collider a;
		Collider b;
		for (int i = 0; i < len; i++)
		{
			for (int j = 0; j < len; j++)
			{
				a = colliders.get(i);
				b = colliders.get(j);
				
				CollisionChecker.check(a, b);
			}
		}
	}
}
