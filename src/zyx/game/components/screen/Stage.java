package zyx.game.components.screen;

import zyx.opengl.shaders.implementations.ScreenShader;

public final class Stage extends DisplayObjectContainer
{

	public static final Stage instance = new Stage(); 

	private Stage()
	{
	}
	
	public final void drawStage()
	{
		draw();
	}

	@Override
	public void transform()
	{
		ScreenShader.MATRIX_MODEL.setIdentity();
	}
}
