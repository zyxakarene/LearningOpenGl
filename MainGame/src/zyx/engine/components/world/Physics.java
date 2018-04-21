package zyx.engine.components.world;

import java.util.ArrayList;
import zyx.engine.components.world.physics.CollisionChecker;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public final class Physics implements IUpdateable
{

	private ArrayList<Collider> physicsColliders;
	private ArrayList<Collider> staticColliders;

	Physics()
	{
		staticColliders = new ArrayList<>();
		physicsColliders = new ArrayList<>();
	}

	void addCollider(Collider collider)
	{
		ArrayList<Collider> list = collider.isStatic ? staticColliders : physicsColliders;

		if (list.contains(collider) == false)
		{
			list.add(collider);
		}
	}

	void removeCollider(Collider collider)
	{
		ArrayList<Collider> list = collider.isStatic ? staticColliders : physicsColliders;

		list.remove(collider);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (elapsedTime >= 200)
		{
			Print.out("High elapsedTime, setting to 16. Was", elapsedTime);
			elapsedTime = 16;
		}
		
		int physLen = physicsColliders.size();
		int staticLen = staticColliders.size();
		
		for (int i = 0; i < physLen; i++)
		{
			physicsColliders.get(i).velocity.z -= 16;
			physicsColliders.get(i).update(timestamp, elapsedTime);
		}

		Collider staticCollider;
		Collider physCollider;
		for (int i = 0; i < physLen; i++)
		{
			physCollider = physicsColliders.get(i);
			
			for (int j = 0; j < staticLen; j++)
			{
				staticCollider = staticColliders.get(j);

				CollisionChecker.check(staticCollider, physCollider);
			}
		}
	}
}
