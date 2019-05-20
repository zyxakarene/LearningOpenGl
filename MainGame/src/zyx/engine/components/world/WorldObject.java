package zyx.engine.components.world;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.opengl.camera.Camera;
import zyx.opengl.camera.IFrustumHideable;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.GeometryUtils;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.math.DecomposedMatrix;
import zyx.utils.math.MatrixUtils;

public abstract class WorldObject implements IPositionable, IDisposeable, IFrustumHideable
{

	protected static final DecomposedMatrix DECOMPOSED_MATRIX = new DecomposedMatrix();

	private static final Vector3f HELPER_POSITION = new Vector3f();
	private static final Vector3f HELPER_DIR = new Vector3f();

	private static final Vector3f HELPER_VEC3 = new Vector3f();
	private static final Vector4f HELPER_VEC4 = new Vector4f();

	public boolean disposed;

	boolean dirty;
	private boolean dirtyInv;
	protected Matrix4f invWorldMatrix;
	protected Matrix4f worldMatrix;
	protected Matrix4f localMatrix;
	public Vector3f position;

	private WorldObject parent;
	private ArrayList<WorldObject> children;

	protected final AbstractShader shader;

	private Collider collider;
	public boolean drawable = true;

	public WorldObject(Shader type)
	{
		invWorldMatrix = SharedPools.MATRIX_POOL.getInstance();
		worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		localMatrix = SharedPools.MATRIX_POOL.getInstance();
		position = SharedPools.VECTOR_POOL.getInstance();

		children = new ArrayList<>();

		shader = ShaderManager.INSTANCE.get(type);
		disposed = false;

		dirty = false;
	}

	public boolean inView()
	{
		getCenter(HELPER_POSITION);

		localToGlobal(HELPER_POSITION, HELPER_POSITION);
		getScale(false, HELPER_VEC3);

		float scale = HELPER_VEC3.x > HELPER_VEC3.y ? HELPER_VEC3.x : HELPER_VEC3.y;
		scale = scale > HELPER_VEC3.z ? scale : HELPER_VEC3.z;

		float radius = getRadius()* scale;

		boolean visible = Camera.getInstance().isInView(HELPER_POSITION, radius);
		return visible;
	}

	public Matrix4f worldMatrix()
	{
		if (dirty)
		{
			Matrix4f.load(localMatrix, worldMatrix);
			if (parent != null)
			{
				Matrix4f.mul(parent.worldMatrix(), worldMatrix, worldMatrix);
			}

			dirty = false;
			dirtyInv = true;
		}

		return worldMatrix;
	}

	public Matrix4f invWorldMatrix()
	{
		if (dirtyInv || dirty)
		{
			Matrix4f.load(worldMatrix(), invWorldMatrix);
			invWorldMatrix.invert();

			dirtyInv = false;
		}

		return invWorldMatrix;
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
		dirtyInv = true;
	}

	protected final void draw()
	{
		if (!drawable)
		{
			return;
		}

		SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());

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

		SharedPools.MATRIX_POOL.releaseInstance(invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);
		SharedPools.VECTOR_POOL.releaseInstance(position);

		children = null;
		position = null;
		worldMatrix = null;
		invWorldMatrix = null;
	}

	public Vector3f globalToLocal(Vector3f point, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		HELPER_VEC4.set(point.x, point.y, point.z, 1);
		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		out.set(HELPER_VEC4.x, HELPER_VEC4.y, HELPER_VEC4.z);
		return out;
	}

	public Vector3f localToGlobal(Vector3f point, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		HELPER_VEC4.set(point.x, point.y, point.z, 1);
		Matrix4f.transform(worldMatrix(), HELPER_VEC4, HELPER_VEC4);

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

		DECOMPOSED_MATRIX.setSource(local ? localMatrix : worldMatrix());
		out.set(DECOMPOSED_MATRIX.rotation);

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

		position.set(HELPER_VEC3);

		localMatrix.m30 = HELPER_VEC3.x;
		localMatrix.m31 = HELPER_VEC3.y;
		localMatrix.m32 = HELPER_VEC3.z;
		localMatrix.m33 = 1;

		updateTransforms(true);
	}

	public void setRotation(float x, float y, float z)
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.set(x, y, z);
		DECOMPOSED_MATRIX.recompose();

		updateTransforms(true);
	}

	public void rotate(float x, float y, float z)
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.x += x;
		DECOMPOSED_MATRIX.rotation.y += y;
		DECOMPOSED_MATRIX.rotation.z += z;
		DECOMPOSED_MATRIX.recompose();

		updateTransforms(true);
	}

	public void setScale(float x, float y, float z)
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.scale.set(x, y, z);
		DECOMPOSED_MATRIX.recompose();

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
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.x = x;
		DECOMPOSED_MATRIX.recompose();

		updateTransforms(true);
	}

	public void setRotY(float y)
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.y = y;
		DECOMPOSED_MATRIX.recompose();

		updateTransforms(true);
	}

	public void setRotZ(float z)
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.z = z;
		DECOMPOSED_MATRIX.recompose();

		updateTransforms(true);
	}

	public float getRotX()
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		return DECOMPOSED_MATRIX.rotation.x;
	}

	public float getRotY()
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		return DECOMPOSED_MATRIX.rotation.y;
	}

	public float getRotZ()
	{
		DECOMPOSED_MATRIX.setSource(localMatrix);
		return DECOMPOSED_MATRIX.rotation.z;
	}
	//</editor-fold>

	@Override
	public void lookAt(float x, float y, float z)
	{
		HELPER_POSITION.set(x, y, z);
		parent.globalToLocal(HELPER_POSITION, HELPER_POSITION);

		getPosition(true, HELPER_DIR);

		Vector3f.sub(HELPER_POSITION, HELPER_DIR, HELPER_DIR);
		HELPER_DIR.normalise();

		MatrixUtils.setDirTo(localMatrix, HELPER_DIR, GeometryUtils.ROTATION_Z);
		updateTransforms(true);
	}

	@Override
	public void setDir(boolean local, Vector3f dir)
	{
		if (local || parent == null)
		{
			MatrixUtils.setDirTo(localMatrix, dir, GeometryUtils.ROTATION_Z);
			updateTransforms(true);
		}
		else
		{
			getPosition(false, HELPER_POSITION);

			HELPER_POSITION.x += dir.x * 100;
			HELPER_POSITION.y += dir.y * 100;
			HELPER_POSITION.z += dir.z * 100;

			lookAt(HELPER_POSITION.x, HELPER_POSITION.y, HELPER_POSITION.z);
		}
	}

	@Override
	public float getRadius()
	{
		return 0;
	}

	@Override
	public void getCenter(Vector3f out)
	{
		out.set(0, 0, 0);
	}
}
