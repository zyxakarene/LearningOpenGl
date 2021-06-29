package zyx.engine.components.screen.base;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.debug.link.DebugInfo;
import zyx.debug.views.base.IDebugIcon;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.events.EventListenerMap;
import zyx.engine.components.screen.base.events.IEventListener;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;
import zyx.engine.components.screen.base.events.types.size.SizeEventType;
import zyx.engine.components.screen.base.events.types.stage.StageEventType;
import zyx.engine.curser.GameCursor;
import zyx.engine.focus.FocusManager;
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

public abstract class DisplayObject implements IPositionable2D, IDisposeable, IDebugIcon, IEventListener
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

	private Rectangle globalClipRect;
	protected Rectangle clipRect;
	protected Matrix4f invWorldMatrix;
	protected Matrix4f worldMatrix;
	protected Matrix4f localMatrix;
	protected Vector2f position;

	public boolean visible;
	public boolean touchable;
	public boolean disposed;
	public boolean mouseChildren;

	private float pivotX;
	private float pivotY;

	public String name;

	public GameCursor hoverIcon;

	protected final ScreenShader shader;
	protected Stage stage;

	private EventListenerMap eventListener;

	public DisplayObject()
	{
		DebugInfo.screenObjects.updateList();

		name = String.format("I%s", instanceCounter++);

		invWorldMatrix = SharedPools.MATRIX_POOL.getInstance();
		worldMatrix = SharedPools.MATRIX_POOL.getInstance();
		localMatrix = SharedPools.MATRIX_POOL.getInstance();
		position = SharedPools.VECTOR_POOL_2F.getInstance();

		visible = true;
		touchable = true;
		disposed = false;
		mouseChildren = true;

		dirty = true;
		dirtyInv = true;

		pivotX = 0f;
		pivotY = 0f;

		shader = ShaderManager.getInstance().<ScreenShader>get(Shader.SCREEN);
	}

	@Override
	public final void dispatchEvent(DisplayObjectEvent event)
	{
		if (eventListener != null)
		{
			event.target = this;
			eventListener.dispatchEvent(event);
		}
		
		if (event.bubbles && parent != null)
		{
			parent.dispatchEvent(event);
		}
		else
		{
			EventCache.returnEvent(event);
		}
	}

	@Override
	public void addListener(IDisplayObjectEventListener listener)
	{
		if (eventListener == null)
		{
			eventListener = new EventListenerMap();
		}
		
		if (eventListener != null)
		{
			eventListener.addListener(listener);
		}
	}

	@Override
	public void removeListener(IDisplayObjectEventListener listener)
	{
		if (eventListener != null)
		{
			eventListener.removeListener(listener);
		}
	}

	protected void makeFocusable(IFocusable focusable)
	{
		FocusManager.getInstance().add(focusable);
	}
	
	public Matrix4f worldMatrix()
	{
		if (dirty)
		{
			localMatrix.translate(new Vector2f(-pivotX, pivotY));
			Matrix4f.load(localMatrix, worldMatrix);
			if (parent != null)
			{
				Matrix4f.mul(parent.worldMatrix(), worldMatrix, worldMatrix);
			}
			localMatrix.translate(new Vector2f(pivotX, -pivotY));

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

	public final void setWidth(float value)
	{
		onSetWidth(value);
		onSizeChanged();
	}
	
	protected abstract void onSetWidth(float value);

	public final void setHeight(float value)
	{
		onSetHeight(value);
		onSizeChanged();
	}
	
	protected abstract void onSetHeight(float value);
	
	private void onSizeChanged()
	{
		int width = (int) getWidth(true);
		int height = (int) getHeight(true);
		
		dispatchEvent(EventCache.get(SizeEventType.Changed).setup(this, width, height));
	}

	protected abstract void onDraw();

	public boolean hasParent()
	{
		return parent != null;
	}

	public DisplayObjectContainer getParent()
	{
		return parent;
	}

	public float getPivotX()
	{
		return pivotX;
	}

	public void setPivotX(float value)
	{
		pivotX = value;
	}

	public float getPivotY()
	{
		return pivotY;
	}

	public void setPivotY(float value)
	{
		pivotY = value;
	}

	public Vector2f getPivot(Vector2f out)
	{
		if (out == null)
		{
			out = new Vector2f();
		}

		out.x = pivotX;
		out.y = pivotY;
		return out;
	}

	public void setPivot(Vector2f value)
	{
		pivotX = value.x;
		pivotY = value.y;
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

	protected final void setParent(DisplayObjectContainer newParent)
	{
		if (newParent != null)
		{
			setStage(newParent.stage, newParent.hoverIcon);
		}
		else
		{
			setStage(null, null);
		}
		
		parent = newParent;
	}
	
	void setStage(Stage stage, GameCursor hoverIcon)
	{
		this.stage = stage;
		
		if (stage == null)
		{
			dispatchEvent(EventCache.get(StageEventType.RemovedFromStage).setup(stage));
		}
		else
		{
			dispatchEvent(EventCache.get(StageEventType.AddedToStage).setup(stage));
		}
		
		if (hoverIcon != null)
		{
			this.hoverIcon = hoverIcon;
		}
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

		SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());
		shader.upload();

		Rectangle oldClipData = null;
		if (clipRect != null)
		{
			if (globalClipRect == null)
			{
				globalClipRect = CLIP_POOL.getInstance();
			}

			oldClipData = CLIP_POOL.getInstance();
			shader.getClipRect(oldClipData);
			getPosition(false, HELPER_VEC2);
			globalClipRect.x = FloatMath.max(oldClipData.x, clipRect.x + HELPER_VEC2.x);
			globalClipRect.y = FloatMath.max(oldClipData.y, clipRect.y + HELPER_VEC2.y);
			globalClipRect.width = FloatMath.min(oldClipData.width, clipRect.x + HELPER_VEC2.x + clipRect.width);
			globalClipRect.height = FloatMath.min(oldClipData.height, clipRect.y + HELPER_VEC2.y + clipRect.height);

			shader.setClipRect(globalClipRect);
		}

		onDraw();

		if (oldClipData != null)
		{
			shader.setClipRect(oldClipData);
			CLIP_POOL.releaseInstance(oldClipData);
		}
	}

	@Override
	public final void dispose()
	{
		if (disposed)
		{
			return;
		}
		DebugInfo.screenObjects.updateList();
		disposed = true;

		removeFromParent(false);
		
		onDispose();

		if (globalClipRect != null)
		{
			CLIP_POOL.releaseInstance(globalClipRect);
			globalClipRect = null;
		}

		if (eventListener != null)
		{
			eventListener.dispose();
			eventListener = null;
		}

		SharedPools.MATRIX_POOL.releaseInstance(invWorldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(worldMatrix);
		SharedPools.MATRIX_POOL.releaseInstance(localMatrix);
		SharedPools.VECTOR_POOL_2F.releaseInstance(position);

		invWorldMatrix = null;
		worldMatrix = null;
		localMatrix = null;
		position = null;
	}
	
	protected void onDispose()
	{
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
		x = (int) x;
		y = (int) y;
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

	public boolean isOnStage()
	{
		return stage != null;
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

		Rectangle hierachyClip = getHierachyClip();
		if (hierachyClip != null)
		{
			boolean outsideClip = x < hierachyClip.x || x > hierachyClip.width || y < hierachyClip.y || y > hierachyClip.height;
			if (outsideClip)
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

	public void setClipRect(int x, int y, int width, int height)
	{
		if (clipRect == null)
		{
			clipRect = new Rectangle();
		}

		clipRect.x = x;
		clipRect.y = y;
		clipRect.width = width;
		clipRect.height = height;
	}

	protected Rectangle getHierachyClip()
	{
		if (globalClipRect != null)
		{
			return globalClipRect;
		}

		if (parent != null)
		{
			return parent.getHierachyClip();
		}

		return null;
	}
}
