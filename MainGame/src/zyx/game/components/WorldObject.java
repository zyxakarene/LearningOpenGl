package zyx.game.components;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;

public abstract class WorldObject implements IPositionable, IDisposeable
{

	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	public WorldObject()
	{
		position = SharedPools.VECTOR_POOL.getInstance();
		rotation = SharedPools.VECTOR_POOL.getInstance();
		scale = SharedPools.VECTOR_POOL.getInstance();
		scale.set(1, 1, 1);

		children = new ArrayList<>();
	}

	public void addChild(WorldObject child)
	{
		if (child.parent != this)
		{
			child.parent = this;
			children.add(child);
		}
	}

	public void removeChild(WorldObject child)
	{
		if (child.parent == this)
		{
			children.remove(child);
		}
		else
		{
			String msg = String.format("Cannot remove child %s when its parent %s != this %s", child, child.parent, this);
			throw new RuntimeException(msg);
		}
	}
	
	public void removeFromParent(boolean dispose)
	{
		if (parent != null)
		{
			parent.removeChild(this);
		}
		
		if (dispose)
		{
			dispose();
		}
	}
	
	public void removeFromParent()
	{
		removeFromParent(false);
	}

	protected final void draw()
	{
		for (WorldObject child : children)
		{
			child.draw();
		}

		onDraw();
	}

	@Override
	public void dispose()
	{
		removeFromParent(false);
		
		for (WorldObject child : children)
		{
			child.dispose();
		}

		children.clear();

		SharedPools.VECTOR_POOL.releaseInstance(position);
		SharedPools.VECTOR_POOL.releaseInstance(rotation);
		SharedPools.VECTOR_POOL.releaseInstance(scale);

		position = null;
		rotation = null;
		scale = null;
		children = null;
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

	abstract protected void onDraw();

	//<editor-fold defaultstate="collapsed" desc="Getter & Setter">
	public void setPosition(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
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

	public void setRotX(float x)
	{
		rotation.x = x;
	}

	public void setRotY(float y)
	{
		rotation.y = y;
	}

	public void setRotZ(float z)
	{
		rotation.z = z;
	}

	public float getRotX()
	{
		return rotation.x;
	}

	public float getRotY()
	{
		return rotation.y;
	}

	public float getRotZ()
	{
		return rotation.z;
	}
	//</editor-fold>

}
