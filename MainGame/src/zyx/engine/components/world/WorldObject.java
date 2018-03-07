package zyx.engine.components.world;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.math.MatrixUtils;

public abstract class WorldObject implements IPositionable, IDisposeable
{

	public boolean disposed;

	private final Matrix4f backupMatrix = new Matrix4f();

	protected Vector3f worldPosition;

	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	protected final WorldShader shader;

	private Collider collider;

	public WorldObject()
	{
		worldPosition = SharedPools.VECTOR_POOL.getInstance();
		position = SharedPools.VECTOR_POOL.getInstance();
		rotation = SharedPools.VECTOR_POOL.getInstance();
		scale = SharedPools.VECTOR_POOL.getInstance();
		scale.set(1, 1, 1);
		worldPosition.set(0, 0, 0);

		children = new ArrayList<>();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
		disposed = false;
	}

	public void setCollider(Collider newCollider)
	{
		if (collider != null)
		{
			collider.setParent(null);
		}
		
		collider = newCollider;
		
		if(collider != null)
		{
			collider.setParent(this);
		}
	}

	public Collider getCollider()
	{
		return collider;
	}

	public void addChild(WorldObject child)
	{
		if (child.parent != this)
		{
			if (child.parent != null)
			{
				child.removeFromParent(false);
			}

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

	protected final void draw()
	{
		onTransform();
		onDraw();

		backupMatrix.load(WorldShader.MATRIX_MODEL);

		for (WorldObject child : children)
		{
			shader.upload();

			child.draw();
			MatrixUtils.getPositionFrom(WorldShader.MATRIX_MODEL, worldPosition);

			WorldShader.MATRIX_MODEL.load(backupMatrix);
		}

	}

	@Override
	public void dispose()
	{
		if (disposed)
		{
			return;
		}
		disposed = true;
		removeFromParent(false);

		for (WorldObject child : children)
		{
			child.dispose();
		}

		children.clear();

		SharedPools.VECTOR_POOL.releaseInstance(worldPosition);
		SharedPools.VECTOR_POOL.releaseInstance(position);
		SharedPools.VECTOR_POOL.releaseInstance(rotation);
		SharedPools.VECTOR_POOL.releaseInstance(scale);

		worldPosition = null;
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

	public Vector3f getScale()
	{
		return scale;
	}

	@Override
	public Vector3f getWorldPosition(Vector3f out)
	{
		out.x = worldPosition.x;
		out.y = worldPosition.y;
		out.z = worldPosition.z;

		return out;
	}

	protected void onDraw()
	{

	}

	protected void onTransform()
	{

	}

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

	public void setScale(float x, float y, float z)
	{
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}
	
	public void setPosition(Vector3f rotation)
	{
		position.x = rotation.x;
		position.y = rotation.y;
		position.z = rotation.z;
	}

	public void setRotation(Vector3f position)
	{
		rotation.x = position.x;
		rotation.y = position.y;
		rotation.z = position.z;
	}

	public Vector3f getPosition(Vector3f out)
	{
		out.x = position.x;
		out.y = position.y;
		out.z = position.z;

		return out;
	}

	public Vector3f getRotation(Vector3f out)
	{
		out.x = rotation.x;
		out.y = rotation.y;
		out.z = rotation.z;

		return out;
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
