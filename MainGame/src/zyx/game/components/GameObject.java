package zyx.game.components;

import zyx.engine.components.world.WorldObject;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorBundle;
import zyx.game.behavior.BehaviorType;
import zyx.opengl.models.SharedWorldModelTransformation;
import zyx.utils.interfaces.IUpdateable;

public class GameObject extends WorldObject implements IUpdateable
{

	private static Vector3f HELPER_POS = new Vector3f();
	private static Vector3f HELPER_ROT = new Vector3f();
	private static Vector3f HELPER_SCALE = new Vector3f();

	private BehaviorBundle behaviors;

	public GameObject()
	{
		behaviors = new BehaviorBundle(this);
	}

	@Override
	protected void onTransform()
	{
		getPosition(true, HELPER_POS);
		getRotation(true, HELPER_ROT);
		getScale(true, HELPER_SCALE);

		SharedWorldModelTransformation.transform(HELPER_POS, HELPER_ROT, HELPER_SCALE);
	}

	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		behaviors.update(timestamp, elapsedTime);
		onUpdate(timestamp, elapsedTime);
	}

	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}

	public final void addBehavior(Behavior behavior)
	{
		behaviors.addBehavior(behavior);
	}
	
	public final void removeBehavior(BehaviorType type)
	{
		behaviors.removeBehavior(type);
	}

	public Behavior getBehaviorById(BehaviorType type)
	{
		return behaviors.getBehaviorById(type);
	}

	@Override
	protected void onDispose()
	{
		behaviors.dispose();
		behaviors = null;
	}
}
