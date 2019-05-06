package zyx.engine.components.world;

import zyx.opengl.deferred.DeferredRenderer;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;

public final class World3D extends WorldObject
{

	public static final World3D instance = new World3D();

	public final Physics physics;

	private DeferredRenderer renderer;

	private World3D()
	{
		super(Shader.WORLD);
		physics = new Physics();

		renderer = DeferredRenderer.getInstance();
	}

	public void drawScene()
	{
		renderer.prepareRender();

		shader.bind();
		draw();

		renderer.draw();

		ParticleManager.getInstance().draw();
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	protected void onTransform()
	{
		SharedShaderObjects.SHARED_MODEL_TRANSFORM.setIdentity();
	}

	@Override
	public String toString()
	{
		return "World3D";
	}

	//<editor-fold defaultstate="collapsed" desc="Getter & Setter">
	private static final String INVALID_METHOD_CALL = "This method is invalid";

	@Override
	public void setPosition(boolean local, float x, float y, float z)
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
