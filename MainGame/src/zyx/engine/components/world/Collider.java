package zyx.engine.components.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public abstract class Collider implements IUpdateable, IDisposeable
{

	public Vector3f velocity;
	public boolean isStatic;
	
	protected WorldObject parent;
	
	public Collider(boolean isStatic)
	{
		this.isStatic = isStatic;
		velocity = SharedPools.VECTOR_POOL.getInstance();
		World3D.instance.physics.addCollider(this);
	}

	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (!isStatic && parent != null)
		{
			float delta = elapsedTime * 0.001f;
			parent.position.x += velocity.x * delta;
			parent.position.y += velocity.y * delta;
			parent.position.z += velocity.z * delta;
		}
		
		onUpdate(timestamp, elapsedTime);
	}
	
	final void setParent(WorldObject parent)
	{
		this.parent = parent;
		
		if (parent == null)
		{
			onParentCleared();
		}
		else
		{
			onParentSet();
		}
	}

	@Override
	public final void dispose()
	{
		SharedPools.VECTOR_POOL.releaseInstance(velocity);
		World3D.instance.physics.removeCollider(this);
		
		velocity = null;
		
		onDispose();
	}
	
	public void getPosition(Vector3f out)
	{
		if (parent != null)
		{
			out.x = parent.position.x;
			out.y = parent.position.y;
			out.z = parent.position.z;
		}
		else
		{
			out.x = 0;
			out.y = 0;
			out.z = 0;
		}
	}
	
	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}
	
	protected void onDispose()
	{
	}

	protected void onParentCleared()
	{
	}
	
	protected void onParentSet()
	{
	}

}
