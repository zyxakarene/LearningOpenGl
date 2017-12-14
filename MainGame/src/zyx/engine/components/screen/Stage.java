package zyx.engine.components.screen;

import zyx.opengl.shaders.implementations.ScreenShader;

public final class Stage extends DisplayObjectContainer
{

	public static final Stage instance = new Stage(); 

	private Stage()
	{
	}
	
	public final void drawStage()
	{
		shader.bind();
		draw();
	}

	@Override
	public void transform()
	{
		ScreenShader.MATRIX_MODEL.setIdentity();
	}
}
