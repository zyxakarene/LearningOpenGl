package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.SimpleModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.interfaces.IUpdateable;

public class Camera implements IUpdateable
{
	
	private final Matrix4f projection = WorldShader.MATRIX_PROJECTION;
	
	public Camera()
	{
		Projection.createPerspective(70f, 0.01f, 2f, projection);
		
		WorldShader.MATRIX_VIEW.setIdentity();
		WorldShader.MATRIX_VIEW.rotate(-1f, SimpleModel.ROTATION_X);
	}

	@Override
	public void update(int elapsedTime)
	{
		
	}
	
}
