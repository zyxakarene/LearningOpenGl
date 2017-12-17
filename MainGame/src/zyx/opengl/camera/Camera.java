package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.interfaces.IPositionable;

public class Camera implements IPositionable
{

	private static Camera instance = new Camera();

	public static Camera getInstance()
	{
		return instance;
	}

	private Vector3f position;
	private Vector3f rotation;
	private boolean initialized;

	private Camera()
	{
		position = new Vector3f();
		rotation = new Vector3f();
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
		Projection.createOrthographic(1f, 2f, ScreenShader.MATRIX_PROJECTION);

		position.set(-18, 11, -25);
		rotation.set(-50, 0, 300);
	}

	@Override
	public Vector3f getPosition()
	{
		return position;
	}

	@Override
	public Vector3f getRotation()
	{
		return rotation;
	}
	
	public void getProjectionMatrix(Matrix4f out)
	{
		out.load(ScreenShader.MATRIX_PROJECTION);
	}
	
	public void getViewMatrix(Matrix4f out)
	{
		out.load(ScreenShader.MATRIX_VIEW);
	}

	@Override
	public Vector3f getWorldPosition(Vector3f out)
	{
		out.x = position.x;
		out.y = position.y;
		out.z = position.z;
		
		return out;
	}
}
