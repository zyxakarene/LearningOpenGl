package zyx.engine.components.world.complexphysics;

import zyx.engine.components.world.*;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class EntityCollider implements IUpdateable, IDisposeable
{

	public Vector3f velocity;
	private float height;
	
	private WorldObject parent;
	
	private static final Vector3f HELPER_POS = new Vector3f();
	
	public EntityCollider(float height)
	{
		velocity = SharedPools.VECTOR_POOL.getInstance();
		
		this.height = height;
		
		World3D.instance.physics.addCollider(this);
	}
	
	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (parent != null)
		{
			float delta = elapsedTime * 0.001f;
			parent.getPosition(false, HELPER_POS);
			
			HELPER_POS.x += velocity.x * delta;
			HELPER_POS.y += velocity.y * delta;
			HELPER_POS.z += velocity.z * delta;
			parent.setPosition(true, HELPER_POS);
		}
	}
	
	@Override
	public final void dispose()
	{
		SharedPools.VECTOR_POOL.releaseInstance(velocity);
		World3D.instance.physics.removeCollider(this);
		
		velocity = null;
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
	
	public void setPosition(Vector3f pos)
	{
		if (parent != null)
		{
			parent.setPosition(false, pos);
		}
	}

	public void setParent(WorldObject parent)
	{
		this.parent = parent;
	}
}
