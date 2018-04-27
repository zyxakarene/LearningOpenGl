package zyx.engine.components.world;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.math.MatrixUtils;

public abstract class WorldObject implements IPositionable, IDisposeable
{

	private static final Vector3f HELPER_VEC3 = new Vector3f();
	private static final Vector4f HELPER_VEC4 = new Vector4f();

	public boolean disposed;

	private boolean dirty;
	private boolean dirtyInv;
	protected Matrix4f _invWorldMatrix;
	protected Matrix4f _worldMatrix;
	protected Matrix4f localMatrix;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	protected final WorldShader shader;

	private Collider collider;

	public WorldObject()
	{
		_invWorldMatrix = SharedPools.MATRIX_POOL.getInstance();
		_worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		localMatrix = SharedPools.MATRIX_POOL.getInstance();

		children = new ArrayList<>();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);
		disposed = false;

		updateTransforms(true);
	}

	public Matrix4f worldMatrix()
	{
		if (dirty)
		{
			Matrix4f.load(localMatrix, _worldMatrix);
			if (parent != null)
			{
				Matrix4f.add(_worldMatrix, parent.worldMatrix(), _worldMatrix);
			}

			dirty = false;
		}

		return _worldMatrix;
	}

	public Matrix4f invWorldMatrix()
	{
		if (dirtyInv || dirty)
		{
			Matrix4f.load(worldMatrix(), _invWorldMatrix);
			_invWorldMatrix.invert();

			dirtyInv = false;
		}

		return _invWorldMatrix;
	}

	public void setCollider(Collider newCollider)
	{
		if (collider != null)
		{
			collider.setParent(null);
		}

		collider = newCollider;

		if (collider != null)
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

			updateTransforms(true);
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

	protected void updateTransforms(boolean alsoChildren)
	{
		if (alsoChildren)
		{
			for (WorldObject child : children)
			{
				child.updateTransforms(alsoChildren);
			}
		}
		dirty = true;

//		onTransform();
//		
//		worldMatrix.load(WorldShader.MATRIX_MODEL);
//		
//		for (WorldObject child : children)
//		{
//			WorldShader.MATRIX_MODEL.load(worldMatrix);
//			child.updateTransforms();
//		}
	}

	protected final void draw()
	{
		WorldShader.MATRIX_MODEL.load(worldMatrix());
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

		SharedPools.MATRIX_POOL.releaseInstance(_invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(_worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);

		children = null;
		_worldMatrix = null;
		_invWorldMatrix = null;
	}

	public Vector3f globalToLocal(Vector3f point, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		HELPER_VEC4.set(point.x, point.y, point.z, 0);
		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		return out;
	}

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		MatrixUtils.getPositionFrom(local ? localMatrix : worldMatrix(), out);

		return out;
	}

	@Override
	public Vector3f getRotation(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
//		out.set(rotation);

		return out;
	}

	@Override
	public Vector3f getScale(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
//		out.set(scale);

		return out;
	}

	@Override
	public Vector3f getDir(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		MatrixUtils.getDirFrom(local ? localMatrix : worldMatrix(), out);

		return out;
	}

	@Override
	public Vector3f getRight(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		MatrixUtils.getRightFrom(local ? localMatrix : worldMatrix(), out);

		return out;
	}

	@Override
	public Vector3f getUp(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		MatrixUtils.getUpFrom(local ? localMatrix : worldMatrix(), out);

		return out;
	}

	@Override
	public void setScale(Vector3f scale)
	{
//		this.scale.set(scale);
		updateTransforms(true);
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
	public void setPosition(float x, float y, float z, boolean local)
	{
		HELPER_VEC3.set(x, y, z);

		if (!local && parent != null)
		{
			parent.globalToLocal(HELPER_VEC3, HELPER_VEC3);
		}
		
		localMatrix.m30 = HELPER_VEC3.x;
		localMatrix.m31 = HELPER_VEC3.y;
		localMatrix.m32 = HELPER_VEC3.z;
		localMatrix.m33 = 1;

		updateTransforms(true);
	}

	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;

		updateTransforms(true);
	}

	public void setScale(float x, float y, float z)
	{
		scale.x = x;
		scale.y = y;
		scale.z = z;

		updateTransforms(true);
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
		updateTransforms();
	}

	public void setY(float y)
	{
		position.y = y;
		updateTransforms();
	}

	public void setZ(float z)
	{
		position.z = z;
		updateTransforms();
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
		updateTransforms();
	}

	public void setRotY(float y)
	{
		rotation.y = y;
		updateTransforms();
	}

	public void setRotZ(float z)
	{
		rotation.z = z;
		updateTransforms();
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
