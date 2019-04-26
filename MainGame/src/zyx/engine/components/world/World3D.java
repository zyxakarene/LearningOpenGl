package zyx.engine.components.world;

import org.lwjgl.opengl.GL11;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.BufferTexture;
import zyx.utils.GameConstants;

public final class World3D extends WorldObject
{

	public static final World3D instance = new World3D();

	public final Physics physics;

	public BufferTexture deferedBuffer;

	private World3D()
	{
		super(Shader.WORLD);
		physics = new Physics();
	}

	public void drawScene()
	{
		deferedBuffer.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

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

	public void initialize()
	{
		if (deferedBuffer == null)
		{
			deferedBuffer = new BufferTexture(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
		}
	}
}
