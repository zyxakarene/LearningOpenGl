package zyx.game.behavior;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public abstract class Behavior implements IUpdateable, IDisposeable
{

	protected final static Vector3f HELPER_DIR = new Vector3f();
	protected final static Vector3f HELPER_POS = new Vector3f();
	protected final static Vector3f HELPER_ROT = new Vector3f();
	
	BehaviorType type;
	boolean active;
	
	protected WorldObject gameObject;

	public Behavior(BehaviorType type)
	{
		this.type = type;
		this.active = true;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	void setGameObject(WorldObject gameObject)
	{
		this.gameObject = gameObject;
	}

	public void initialize()
	{
	}

	@Override
	public void dispose()
	{
		type = null;
		gameObject = null;
	}
}
