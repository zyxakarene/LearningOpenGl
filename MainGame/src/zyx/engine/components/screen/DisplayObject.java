package zyx.engine.components.screen;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.world.WorldObject;
import zyx.game.controls.SharedPools;
import zyx.game.controls.input.MouseData;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable2D;
import zyx.utils.math.DecomposedMatrix;
import zyx.utils.math.MatrixUtils;

public abstract class DisplayObject implements IPositionable2D, IDisposeable
{

	protected static final Vector2f HELPER_VEC2 = new Vector2f();
	protected static final Vector3f HELPER_VEC3 = new Vector3f();
	protected static final Vector4f HELPER_VEC4 = new Vector4f();

	private static final Vector3f SCALE_3F = new Vector3f(1, 1, 1);

	protected static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;

	private DisplayObjectContainer parent;
	private boolean dirty;
	private boolean dirtyInv;

	protected Matrix4f invWorldMatrix;
	protected Matrix4f worldMatrix;
	protected Matrix4f localMatrix;
	public Vector2f position;

	public boolean visible;
	public boolean buttonMode;
	public boolean touchable;
	public boolean focusable;
	public boolean disposed;


	protected final ScreenShader shader;

	public DisplayObject()
	{
		invWorldMatrix = SharedPools.MATRIX_POOL.getInstance();
		worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		localMatrix = SharedPools.MATRIX_POOL.getInstance();
		position = SharedPools.VECTOR_POOL_2F.getInstance();

		visible = true;
		buttonMode = false;
		touchable = true;
		focusable = false;
		disposed = false;
		
		dirty = true;
		dirtyInv = true;

		shader = (ScreenShader) ShaderManager.INSTANCE.get(Shader.SCREEN);
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

	protected void updateTransforms(boolean alsoChildren)
	{
		dirty = true;
	}

	public abstract float getWidth();

	public abstract float getHeight();

	public abstract void setWidth(float value);

	public abstract void setHeight(float value);

	abstract void onDraw();

	public boolean hasParent()
	{
		return parent != null;
	}

	public DisplayObjectContainer getParent()
	{
		return parent;
	}

	protected final void setParent(DisplayObjectContainer parent)
	{
		this.parent = parent;
	}

	public void removeFromParent(boolean dispose)
	{
		if (parent != null)
		{
			parent.removeChild(this);

			if (dispose)
			{
				dispose();
			}
		}
	}

	final void draw()
	{
		if (!visible)
		{
			return;
		}

		SharedShaderObjects.SHARED_MODEL_TRANSFORM.load(worldMatrix());
		shader.upload();

		onDraw();
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

		SharedPools.MATRIX_POOL.releaseInstance(invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);
		SharedPools.VECTOR_POOL_2F.releaseInstance(position);

		invWorldMatrix = null;
		worldMatrix = null;
		localMatrix = null;
	}

	public Vector2f globalToLocal(Vector2f point, Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		HELPER_VEC4.set(point.x, point.y, 0, 0);
		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		out.set(HELPER_VEC4.x, HELPER_VEC4.y);
		return out;
	}

	public void setPosition(boolean local, float x, float y)
	{
		HELPER_VEC2.set(x, y);

		if (!local && parent != null)
		{
			parent.globalToLocal(HELPER_VEC2, HELPER_VEC2);
		}

		if (local)
		{
			position.set(x, y);
		}

		localMatrix.m30 = HELPER_VEC2.x;
		localMatrix.m31 = -HELPER_VEC2.y;
		localMatrix.m32 = 0;
		localMatrix.m33 = 1;

		updateTransforms(true);
	}

	@Override
	public void setPosition(boolean local, Vector2f pos)
	{
		setPosition(local, pos.x, pos.y);
	}

	public void setScale(float x, float y)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.scale.set(x, y, 1);
		decomposed.recompose();

		updateTransforms(true);
	}

	@Override
	public void setScale(Vector2f scale)
	{
		setScale(scale.x, scale.y);
	}

	@Override
	public void setRotation(float rotation)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(localMatrix);
		decomposed.rotation.set(0, 0, rotation);
		decomposed.recompose();

		updateTransforms(true);
	}

	@Override
	public Vector2f getPosition(boolean local, Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		MatrixUtils.getPositionFrom(local ? localMatrix : worldMatrix(), HELPER_VEC3);

		out.x = HELPER_VEC3.x;
		out.y = HELPER_VEC3.y;

		return out;
	}

	@Override
	public float getRotation(boolean local)
	{
		DecomposedMatrix decomposed = new DecomposedMatrix(local ? localMatrix : worldMatrix());

		return decomposed.rotation.z;
	}

	@Override
	public Vector2f getScale(boolean local, Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		MatrixUtils.getScaleFrom(local ? localMatrix : worldMatrix(), HELPER_VEC3);

		out.x = HELPER_VEC3.x;
		out.y = HELPER_VEC3.y;

		return out;
	}

	public void setX(float x)
	{
		setPosition(true, x, position.y);
	}

	public void setY(float y)
	{
		setPosition(true, position.x, y);
	}

	public float getX()
	{
		return position.x;
	}

	public float getY()
	{
		return position.y;
	}

	public boolean hitTest(int x, int y)
	{
		if (!touchable || !visible)
		{
			return false;
		}
		
		HELPER_VEC4.x = MouseData.data.x;
		HELPER_VEC4.y = -MouseData.data.y;
		HELPER_VEC4.z = -1;
		HELPER_VEC4.w = 1;

		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		boolean collision = HELPER_VEC4.x >= 0 && HELPER_VEC4.y <= 0 && HELPER_VEC4.x <= getWidth() && HELPER_VEC4.y >= -getHeight();

		return collision;
	}

	void keyDown(char charValue)
	{
	}

}
