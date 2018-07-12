package zyx.engine.components.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public abstract class Collider implements IUpdateable, IDisposeable
{

	public Vector3f velocity;
	public boolean isStatic;
	
	protected WorldObject parent;
	
	private static final Vector3f HELPER_POS = new Vector3f();
	
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
			parent.getPosition(false, HELPER_POS);
			
			HELPER_POS.x += velocity.x * delta;
			HELPER_POS.y += velocity.y * delta;
			HELPER_POS.z += velocity.z * delta;
			parent.setPosition(true, HELPER_POS);
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
			parent.getPosition(false, out);
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
