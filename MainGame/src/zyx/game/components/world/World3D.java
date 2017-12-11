package zyx.game.components.world;

import zyx.game.components.WorldObject;
import zyx.opengl.shaders.implementations.WorldShader;

public final class World3D extends WorldObject
{

	public static final World3D instance = new World3D();
	
	private World3D()
	{
	}

	public void drawScene()
	{
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

}
