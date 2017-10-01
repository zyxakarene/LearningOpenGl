package zyx.game.components.screen;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.interfaces.IPositionable2D;

public abstract class DisplayObject implements IPositionable2D
{

	public Vector2f position;
	public float rotation;
	public Vector2f scale;
	
	private static final Vector3f SCALE_3F = new Vector3f(1, 1, 1);
	
	protected final ScreenShader shader;

	public DisplayObject()
	{
		position = new Vector2f(0, 0);
		rotation = 0f;
		scale = new Vector2f(1, 1);
		
		shader = (ScreenShader) ShaderManager.INSTANCE.get(Shader.SCREEN);
	}

	public abstract float getWidth();
	public abstract float getHeight();
	protected abstract void draw();

	public void transform()
	{
		SCALE_3F.x = scale.x;
		SCALE_3F.y = scale.y;
		
		ScreenShader.MATRIX_MODEL.translate(position);
		ScreenShader.MATRIX_MODEL.rotate(FloatMath.toRadians(-rotation), GeometryUtils.ROTATION_Z);
		ScreenShader.MATRIX_MODEL.scale(SCALE_3F);
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

	
}
