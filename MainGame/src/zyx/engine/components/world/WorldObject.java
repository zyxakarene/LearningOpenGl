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

	protected Matrix4f worldMatrix;
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	protected final WorldShader shader;

	private Collider collider;

	public WorldObject()
	{
		position = SharedPools.VECTOR_POOL.getInstance();
		rotation = SharedPools.VECTOR_POOL.getInstance();
		scale = SharedPools.VECTOR_POOL.getInstance();
		worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		scale.set(1, 1, 1);

		children = new ArrayList<>();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
		disposed = false;
		
		updateWorldMatrix();
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
			
			updateWorldMatrix();
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
	
	public void removeChildren(boolean dispose)
	{
		for (WorldObject child : children)
		{
			removeChild(child);
			
			if (dispose)
			{
				child.dispose();
			}
		}
	}

	public void removeFromParent(boolean dispose)
	{
		if (parent != null)
		{
			parent.removeChild(this);
			parent = null;
		}

		if (dispose)
		{
			dispose();
		}
	}

	protected void updateWorldMatrix()
	{
		onTransform();
		
		worldMatrix.load(WorldShader.MATRIX_MODEL);
		
		for (WorldObject child : children)
		{
			WorldShader.MATRIX_MODEL.load(worldMatrix);
			child.updateWorldMatrix();
		}
	}
	
	protected final void draw()
	{
		WorldShader.MATRIX_MODEL.load(worldMatrix);
		shader.upload();
		
		onDraw();

		for (WorldObject child : children)
		{
			child.draw();
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

		SharedPools.VECTOR_POOL.releaseInstance(position);
		SharedPools.VECTOR_POOL.releaseInstance(rotation);
		SharedPools.VECTOR_POOL.releaseInstance(scale);
		SharedPools.MATRIX_POOL.releaseInstance(worldMatrix);

		position = null;
		rotation = null;
		scale = null;
		children = null;
		worldMatrix = null;
	}

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		if (local)
		{
			out.set(position);
		}
		else
		{
			MatrixUtils.getPositionFrom(worldMatrix, out);
		}
		
		return out;
	}

	@Override
	public Vector3f getRotation(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		out.set(rotation);
		
		return out;
	}

	@Override
	public Vector3f getScale(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		out.set(scale);
		
		return out;
	}

	@Override
	public Vector3f getDir(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		out.set(0, 0, 0);
		
		return out;
	}

	@Override
	public void setScale(Vector3f scale)
	{
		this.scale.set(scale);
		updateWorldMatrix();
	}
	
	protected void onDraw()
	{

	}

	protected void onTransform()
	{

	}
	
	protected void transform()
	{
		for (WorldObject child : children)
		{
			child.transform();
		}
	}

	//<editor-fold defaultstate="collapsed" desc="Getter & Setter">
	public void setPosition(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
		
		updateWorldMatrix();
	}

	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
		
		updateWorldMatrix();
	}

	public void setScale(float x, float y, float z)
	{
		scale.x = x;
		scale.y = y;
		scale.z = z;
		
		updateWorldMatrix();
	}
	
	@Override
	public void setPosition(Vector3f pos)
	{
		setPosition(pos.x, pos.y, pos.z);
	}

	@Override
	public void setRotation(Vector3f rot)
	{
		setRotation(rot.x, rot.y, rot.z);
	}

	public void setX(float x)
	{
		position.x = x;
		updateWorldMatrix();
	}

	public void setY(float y)
	{
		position.y = y;
		updateWorldMatrix();
	}

	public void setZ(float z)
	{
		position.z = z;
		updateWorldMatrix();
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
		updateWorldMatrix();
	}

	public void setRotY(float y)
	{
		rotation.y = y;
		updateWorldMatrix();
	}

	public void setRotZ(float z)
	{
		rotation.z = z;
		updateWorldMatrix();
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

	@Override
	public void setDir(Vector3f dir)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
