package zyx.game.behavior;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.components.GameObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public abstract class Behavior implements IUpdateable, IDisposeable
{

	protected final static Vector3f HELPER_DIR = new Vector3f();
	protected final static Vector3f HELPER_POS = new Vector3f();
	protected final static Vector3f HELPER_ROT = new Vector3f();
	
	BehaviorType type;
	boolean active;
	
	protected GameObject gameObject;

	public Behavior(BehaviorType type)
	{
		this.type = type;
		this.active = true;
	}

	public final void setActive(boolean value)
	{
		if (active != value)
		{
			active = value;
			
			if (value)
			{
				onActivate();
			}
			else
			{
				onDeactivate();
			}
		}
	}
	
	void setGameObject(GameObject gameObject)
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

	protected void onActivate()
	{
	}

	protected void onDeactivate()
	{
	}
}
