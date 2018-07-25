package zyx.engine.components.screen;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable2D;

public abstract class DisplayObject implements IPositionable2D, IDisposeable
{
	private static final Vector3f SCALE_3F = new Vector3f(1, 1, 1);

	protected static final Matrix4f MATRIX_MODEL = SharedShaderObjects.SHARED_MODEL_TRANSFORM;
	
	private DisplayObjectContainer parent;
	
	public Vector2f position;
	public float rotation;
	public Vector2f scale;
	public boolean visible;
	
	protected final ScreenShader shader;

	public DisplayObject()
	{
		position = SharedPools.VECTOR_POOL_2F.getInstance();
		scale = SharedPools.VECTOR_POOL_2F.getInstance();
		rotation = 0f;
		visible = true;
		
		position.x = 0;
		position.y = 0;
		scale.x = 1;
		scale.y = 1;
		
		shader = (ScreenShader) ShaderManager.INSTANCE.get(Shader.SCREEN);
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
		if(parent != null)
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
		if (visible)
		{
			onDraw();
		}
	}

	public void transform()
	{
		SCALE_3F.x = scale.x;
		SCALE_3F.y = scale.y;
		
		position.y = -position.y;

		MATRIX_MODEL.translate(position);
		MATRIX_MODEL.rotate(FloatMath.toRadians(-rotation), GeometryUtils.ROTATION_Z);
		MATRIX_MODEL.scale(SCALE_3F);
		
		position.y = -position.y;
	}
	
	public float getScaleX()
	{
		return scale.x;
	}

	public float getScaleY()
	{
		return scale.y;
	}

	@Override
	public Vector2f getPosition()
	{
		return position;
	}

	@Override
	public float getRotation()
	{
		return rotation;
	}

	@Override
	public void dispose()
	{
		removeFromParent(false);
		
		SharedPools.VECTOR_POOL_2F.releaseInstance(position);
		SharedPools.VECTOR_POOL_2F.releaseInstance(scale);
		
		position = null;
		scale = null;
	}

	
}
