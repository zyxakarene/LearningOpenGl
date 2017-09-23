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
		
	private final BehaviorBundle behaviors;

	public GameObject()
	{
		position = new Vector3f();
		rotation = new Vector3f();
		
		behaviors = new BehaviorBundle(this);
	}
	
	@Override
	public void update(int elapsedTime)
	{
		behaviors.update(elapsedTime);
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
}
