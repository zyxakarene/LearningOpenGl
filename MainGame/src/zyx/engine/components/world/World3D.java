package zyx.engine.components.world;

import zyx.opengl.shaders.implementations.WorldShader;

public final class World3D extends WorldObject
{

	public static final World3D instance = new World3D();
	
	public final Physics physics;

	private World3D()
	{
		physics = new Physics();
	}

	public void drawScene()
	{
		shader.bind();
		draw();
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	protected void onTransform()
	{
		WorldShader.MATRIX_MODEL.setIdentity();
	}

	//<editor-fold defaultstate="collapsed" desc="Getter & Setter">
	private static final String INVALID_METHOD_CALL = "This method is invalid";

	@Override
	public void setPosition(float x, float y, float z)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setRotation(float x, float y, float z)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setX(float x)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setY(float y)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setZ(float z)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setRotX(float x)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setRotY(float y)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}

	@Override
	public void setRotZ(float z)
	{
		throw new IllegalArgumentException(INVALID_METHOD_CALL);
	}
	//</editor-fold>

}
