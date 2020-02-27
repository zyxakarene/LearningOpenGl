package zyx.engine.components.screen.base;

import zyx.engine.touch.ITouched;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.debug.views.base.IDebugIcon;
import zyx.engine.curser.GameCursor;
import zyx.engine.touch.MouseTouchManager;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable2D;
import zyx.utils.math.DecomposedMatrix;
import zyx.utils.math.MatrixUtils;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableRectangle;

public abstract class DisplayObject implements IPositionable2D, IDisposeable, IDebugIcon
{
	private static int instanceCounter;

	private static final ObjectPool<Rectangle> CLIP_POOL = new GenericPool(PoolableRectangle.class, 10);

	protected static final DecomposedMatrix DECOMPOSED_MATRIX = new DecomposedMatrix();

	protected static final Vector2f HELPER_VEC2 = new Vector2f();
	protected static final Vector3f HELPER_VEC3 = new Vector3f();
	protected static final Vector4f HELPER_VEC4 = new Vector4f();

	private DisplayObjectContainer parent;
	private boolean dirty;
	private boolean dirtyInv;

	protected Rectangle clipRect;
	protected Matrix4f invWorldMatrix;
	protected Matrix4f worldMatrix;
	protected Matrix4f localMatrix;
	protected Vector2f position;

	public boolean visible;
	public boolean buttonMode;
	public boolean touchable;
	public boolean focusable;
	public boolean disposed;

	public String name;

	public GameCursor hoverIcon;

	protected final ScreenShader shader;
	protected Stage stage;

	public DisplayObject()
	{
		DebugDisplayObjectList.updateList();
		
		name = String.format("I%s", instanceCounter++);
		
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

		shader = ShaderManager.getInstance().<ScreenShader>get(Shader.SCREEN);
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

			onWorldMatrixUpdated();
		}

		return worldMatrix;
	}

	protected void onWorldMatrixUpdated()
	{
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

	protected abstract void onDraw();

	public boolean hasParent()
	{
		return parent != null;
	}

	public DisplayObjectContainer getParent()
	{
		return parent;
	}

	public float getWidth(boolean local)
	{
		float width = getWidth();
		if (!local && parent != null)
		{
			float scaleX = parent.getScale(false, HELPER_VEC2).x;
			width = width * scaleX;
		}

		return width;
	}

	public float getHeight(boolean local)
	{
		float height = getHeight();
		if (!local && parent != null)
		{
			float scaleY = parent.getScale(false, HELPER_VEC2).y;
			height = height * scaleY;
		}

		return height;
	}

	protected final void setParent(DisplayObjectContainer parent)
	{
		DebugDisplayObjectList.updateList();
		
		if (parent != null && parent.stage != null)
		{
			stage = parent.stage;
		}
		else
		{
			stage = null;
		}

		this.parent = parent;
	}

	public void removeFromParent(boolean dispose)
	{
		if (parent != null)
		{
			DebugDisplayObjectList.updateList();
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

		SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());
		shader.upload();

		Rectangle oldClipData = null;
		if (clipRect != null)
		{
			oldClipData = CLIP_POOL.getInstance();
			shader.getClipRect(oldClipData);
			getPosition(true, HELPER_VEC2);
			float clipX = FloatMath.max(oldClipData.x, clipRect.x + HELPER_VEC2.x);
			float clipY = FloatMath.max(oldClipData.y, clipRect.y + HELPER_VEC2.y);
			float clipW = FloatMath.min(oldClipData.width, clipRect.x + HELPER_VEC2.x + clipRect.width);
			float clipH = FloatMath.min(oldClipData.height, clipRect.y + HELPER_VEC2.y + clipRect.height);
			
			shader.setClipRect(clipX, clipW, clipY, clipH);
		}
		
		onDraw();
		
		if (oldClipData != null)
		{
			shader.setClipRect(oldClipData.x, oldClipData.width, oldClipData.y, oldClipData.height);
			CLIP_POOL.releaseInstance(oldClipData);
		}
	}

	@Override
	public void dispose()
	{
		if (disposed)
		{
			return;
		}
		DebugDisplayObjectList.updateList();
		disposed = true;

		removeFromParent(false);

		SharedPools.MATRIX_POOL.releaseInstance(invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);
		SharedPools.VECTOR_POOL_2F.releaseInstance(position);

		invWorldMatrix = null;
		worldMatrix = null;
		localMatrix = null;
		position = null;
	}

	public Vector2f globalToLocal(Vector2f point, Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		HELPER_VEC4.set(point.x, -point.y, 0, 1);
		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		out.set(HELPER_VEC4.x, -HELPER_VEC4.y);
		return out;
	}

	public Vector2f localToGlobal(Vector2f point, Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		HELPER_VEC4.set(point.x, point.y, 0, 1);
		Matrix4f.transform(worldMatrix(), HELPER_VEC4, HELPER_VEC4);

		out.set(HELPER_VEC4.x, HELPER_VEC4.y);
		return out;
	}

	public void setPosition(boolean local, float x, float y)
	{
		x = (int)x;
		y = (int)y;
		HELPER_VEC2.set(x, y);

		if (!local && parent != null)
		{
			parent.globalToLocal(HELPER_VEC2, HELPER_VEC2);
		}

		if (local)
		{
			position.set(x, y);
		}
		else
		{
			position.set(HELPER_VEC2.x, -HELPER_VEC2.y);
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
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.scale.set(x, y, 1);
		DECOMPOSED_MATRIX.recompose();

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
		DECOMPOSED_MATRIX.setSource(localMatrix);
		DECOMPOSED_MATRIX.rotation.set(0, 0, rotation);
		DECOMPOSED_MATRIX.recompose();

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
		out.y = -HELPER_VEC3.y;

		return out;
	}

	@Override
	public float getRotation(boolean local)
	{
		DECOMPOSED_MATRIX.setSource(local ? localMatrix : worldMatrix());

		return DECOMPOSED_MATRIX.rotation.z;
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

	protected void addTouchListener(ITouched listener)
	{
		MouseTouchManager.getInstance().registerTouch(this, listener);
	}

	protected void removeTouchListener(ITouched listener)
	{
		MouseTouchManager.getInstance().unregisterTouch(this, listener);
	}

	@Override
	public String getDebugIcon()
	{
		return "displayobject.png";
	}

	@Override
	public final String getDebugIconFolder()
	{
		return "screen";
	}
	
	public boolean hitTest(int x, int y)
	{
		if (!touchable || !visible)
		{
			return false;
		}

		if (clipRect != null)
		{
			boolean insideClip = x > clipRect.x && x < clipRect.width && y > clipRect.y && y < clipRect.height;
			if (!insideClip)
			{
				return false;
			}
		}
		
		HELPER_VEC4.x = x;
		HELPER_VEC4.y = -y;
		HELPER_VEC4.z = -1;
		HELPER_VEC4.w = 1;

		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);

		if (parent != null)
		{
			getScale(true, HELPER_VEC2);
		}

		float scaleX = 1 / HELPER_VEC2.x;
		float scaleY = 1 / HELPER_VEC2.y;
		float w = getWidth() * scaleX;
		float h = getHeight() * scaleY;

		boolean collision = HELPER_VEC4.x >= 0 && HELPER_VEC4.y <= 0 && HELPER_VEC4.x <= w && HELPER_VEC4.y >= -h;

		return collision;
	}
}
