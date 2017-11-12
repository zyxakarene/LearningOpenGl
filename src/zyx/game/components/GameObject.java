package zyx.game.components;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorBundle;
import zyx.game.behavior.BehaviorType;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.interfaces.IUpdateable;

public class GameObject implements IUpdateable, IPositionable
{

	protected final Vector3f position;
	protected final Vector3f rotation;
	protected final Vector3f scale;

	private final BehaviorBundle behaviors;

	public GameObject()
	{
		position	=	new Vector3f(0, 0, 0);
		rotation	=	new Vector3f(0, 0, 0);
		scale		=	new Vector3f(1, 1, 1);

		behaviors = new BehaviorBundle(this);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		behaviors.update(timestamp, elapsedTime);
	}

	public final void addBehavior(Behavior behavior)
	{
		behaviors.addBehavior(behavior);
	}

	public Behavior getBehaviorById(BehaviorType type)
	{
		return behaviors.getBehaviorById(type);
	}

	@Override
	public Vector3f getPosition()
	{
		return position;
	}

	@Override
	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setPosition(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void setX(float x)
	{
		position.x = x;
	}

	public void setY(float y)
	{
		position.y = y;
	}

	public void setZ(float z)
	{
		position.z = z;
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public float getY()
	{
		return position.y;
	}
	
	public float getZ()
	{
		return position.z;
	}
}
