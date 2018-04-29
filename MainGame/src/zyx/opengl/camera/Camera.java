package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.calculating.RayPicker;
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
		Projection.createOrthographic(1f, 2f, ScreenShader.MATRIX_PROJECTION, 2);
		
//		Projection.createOrthographic(0.01f, 1000f, WorldShader.MATRIX_PROJECTION, 16);

		rotation.set(-90, 0, 300);
		
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

	@Override
	public Vector3f getPosition(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		out.set(position);
		
		return out;
	}

	@Override
	public Vector3f getRotation(boolean local, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}
		out.set(rotation);
		
		return out;
	}

	@Override
	public void setPosition(boolean local, Vector3f pos)
	{
		position.set(pos);
	}

	@Override
	public void setRotation(Vector3f rot)
	{
		rotation.set(rot);
	}
	
	@Override
	public Vector3f getScale(boolean local, Vector3f out)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Vector3f getDir(boolean local, Vector3f out)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setDir(boolean local, Vector3f dir)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setScale(boolean local, Vector3f scale)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Vector3f getUp(boolean local, Vector3f out)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Vector3f getRight(boolean local, Vector3f out)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
