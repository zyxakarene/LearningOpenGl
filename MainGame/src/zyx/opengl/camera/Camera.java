package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;

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
		super(Shader.WORLD);
		initialized = false;
	}

	public void initialize()
	{
		if (initialized)
		{
			return;
		}
		
		initialized = true;
		Projection.createPerspective(70f, 0.001f, 2f, SharedShaderObjects.SHARED_PROJECTION_TRANSFORM);
		Projection.createOrthographic(1f, 2f, SharedShaderObjects.SHARED_ORTHOGRAPHIC_TRANSFORM, 2);
		
//		Projection.createOrthographic(0.01f, 1000f, WorldShader.MATRIX_PROJECTION, 16);
		
		RayPicker.getInstance().setProjectionMatrix(SharedShaderObjects.SHARED_PROJECTION_TRANSFORM);
		
		setRotation(-90, 0, 0);
	}
	
	public void getProjectionMatrix(Matrix4f out)
	{
		out.load(SharedShaderObjects.SHARED_PROJECTION_TRANSFORM);
	}
	
	public void getViewMatrix(Matrix4f out)
	{
		out.load(SharedShaderObjects.SHARED_VIEW_TRANSFORM);
	}
}
