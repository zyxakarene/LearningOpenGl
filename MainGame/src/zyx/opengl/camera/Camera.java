package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.WorldShader;

public class Camera extends WorldObject
{

	private static Camera instance = new Camera();

	public static Camera getInstance()
	{
		return instance;
	}

	private boolean initialized;

	private Camera()
	{
		initialized = false;
	}

	public void initialize()
	{
		if (initialized)
		{
			return;
		}
		
		initialized = true;
		Projection.createPerspective(70f, 0.001f, 2f, WorldShader.MATRIX_PROJECTION);
		Projection.createOrthographic(1f, 2f, ScreenShader.MATRIX_PROJECTION, 2);
		
//		Projection.createOrthographic(0.01f, 1000f, WorldShader.MATRIX_PROJECTION, 16);
		
		RayPicker.getInstance().setProjectionMatrix(WorldShader.MATRIX_PROJECTION);
	}
	
	public void getProjectionMatrix(Matrix4f out)
	{
		out.load(WorldShader.MATRIX_PROJECTION);
	}
	
	public void getViewMatrix(Matrix4f out)
	{
		out.load(WorldShader.MATRIX_VIEW);
	}
}
