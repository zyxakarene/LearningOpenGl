package zyx.engine.components.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.AmbientOcclusionRenderer;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;

public final class World3D extends WorldObject
{

	public static final World3D instance = new World3D();

	public final Physics physics;

	private DeferredRenderer renderer;
	private DepthRenderer depth;
	private AmbientOcclusionRenderer ambientOcclusion;

	private GameSun sun;
	
	private World3D()
	{
		super(Shader.WORLD);
		physics = new Physics();

		renderer = DeferredRenderer.getInstance();
		depth = DepthRenderer.getInstance();
		ambientOcclusion = AmbientOcclusionRenderer.getInstance();
		
		sun = new GameSun();
		Vector3f startSunDir = new Vector3f(-0.0626f, 0.7103f, -0.701f);
		setSunDir(startSunDir);
	}

	public void drawScene()
	{
		depth.prepareRender();
		ambientOcclusion.prepareRender();
		renderer.prepareRender();

		shader.bind();
		draw();

		ambientOcclusion.drawAmbientOcclusion();
		
		renderer.draw();

		ParticleManager.getInstance().draw();
	}

	public void setSunDir(Vector3f dir)
	{
		sun.setDir(true, dir);
	}

	public void setSunRotation(Vector3f rotation)
	{
		sun.setRotation(rotation);
	}
	
	public void setSunEnabled(boolean enabled)
	{
		sun.setEnabled(enabled);
	}
	
	@Override
	protected void onDraw()
	{
	}

	@Override
	protected void onTransform()
	{
		SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.setIdentity();
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
