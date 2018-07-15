package zyx.engine.components.world;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.math.DecomposedMatrix;
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
	public Vector3f position;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	protected final AbstractShader shader;

	private Collider collider;
	public boolean drawable = true;

	public WorldObject(Shader type)
	{
		_invWorldMatrix = SharedPools.MATRIX_POOL.getInstance();
		_worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		localMatrix = SharedPools.MATRIX_POOL.getInstance();
		position = SharedPools.VECTOR_POOL.getInstance();

		children = new ArrayList<>();

		shader = ShaderManager.INSTANCE.get(type);
		disposed = false;

		dirty = false;
	}

	public Matrix4f worldMatrix()
	{
		if (dirty)
		{
			Matrix4f.load(localMatrix, _worldMatrix);
			if (parent != null)
			{
				Matrix4f.mul(parent.worldMatrix(), _worldMatrix, _worldMatrix);
			}

			dirty = false;
			dirtyInv = true;
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
			child.parent = null;
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

	public boolean hasParent()
	{
		return parent != null;
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
	}

	protected final void draw()
	{
		if (!drawable)
		{
			return;
		}
		
		SharedShaderObjects.SHARED_MODEL_TRANSFORM.load(worldMatrix());
		shader.bind();
		shader.upload();

		onDraw();

		for (WorldObject child : children)
		{
			child.draw();
		}
	}

	@Override
	public final void dispose()
	{
		if (disposed)
		{
			return;
		}
		onDispose();
		
		disposed = true;
		removeFromParent(false);

		int len = children.size();
		for (int i = len - 1; i >= 0; i--)
		{
			children.get(i).dispose();
		}

		children.clear();

		SharedPools.MATRIX_POOL.releaseInstance(_invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(_worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);
		SharedPools.VECTOR_POOL.releaseInstance(position);

		children = null;
		position = null;
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

		out.set(HELPER_VEC4.x, HELPER_VEC4.y, HELPER_VEC4.z);
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
		
		DecomposedMatrix decomposed = new DecomposedMatrix(local ? localMatrix : worldMatrix());
		out.set(decomposed.rotation);

		return out;
	}

	@Override
	public Vector3f getScale(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		MatrixUtils.getScaleFrom(local ? localMatrix : worldMatrix(), out);

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
		setScale(scale.x, scale.y, scale.z);
	}

	protected void onDraw()
	{

	}

	protected void onTransform()
	{

	}
	
	protected void onDispose()
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
	public void setPosition(boolean local, float x, float y, float z)
	{
		HELPER_VEC3.set(x, y, z);

		if (!local && parent != null)
		{
			parent.globalToLocal(HELPER_VEC3, HELPER_VEC3);
		}
		
		if (local)
		{
			position.set(x, y, z);
		}
		
		localMatrix.m30 = HELPER_VEC3.x;
		localMatrix.m31 = HELPER_VEC3.y;
		localMatrix.m32 = HELPER_VEC3.z;
		localMatrix.m33 = 1;

		updateTransforms(true);
	}

	public void setRotation(float x, float y, float z)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.rotation.set(x, y, z);
		decomposed.recompose();

		updateTransforms(true);
	}

	public void setScale(float x, float y, float z)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.scale.set(x, y, z);
		decomposed.recompose();

		updateTransforms(true);
	}

	@Override
	public void setPosition(boolean local, Vector3f pos)
	{
		setPosition(local, pos.x, pos.y, pos.z);
	}

	@Override
	public void setRotation(Vector3f rot)
	{
		setRotation(rot.x, rot.y, rot.z);
	}

	public void setX(float x)
	{
		setPosition(true, x, position.y, position.z);
	}

	public void setY(float y)
	{
		setPosition(true, position.x, y, position.z);
	}

	public void setZ(float z)
	{
		setPosition(true, position.x, position.y, z);
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
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.rotation.x = x;
		decomposed.recompose();

		updateTransforms(true);
	}

	public void setRotY(float y)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.rotation.y = y;
		decomposed.recompose();

		updateTransforms(true);
	}

	public void setRotZ(float z)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.rotation.z = z;
		decomposed.recompose();
		
		updateTransforms(true);
	}

	public float getRotX()
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		return decomposed.rotation.x;
	}

	public float getRotY()
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		return decomposed.rotation.y;
	}

	public float getRotZ()
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		return decomposed.rotation.z;
	}
	//</editor-fold>

	@Override
	public void setDir(boolean local, Vector3f dir)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
