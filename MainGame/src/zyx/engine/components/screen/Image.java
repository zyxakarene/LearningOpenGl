package zyx.engine.components.screen;

import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.GameTexture;
import zyx.game.controls.textures.TextureManager;

public class Image extends DisplayObject
{

	private ScreenModel model;
	
	public Image(String texture)
	{
		GameTexture gameTexture = TextureManager.getTexture(texture);
		model = new ScreenModel(gameTexture);
	}
	
	@Override
	public float getWidth()
	{
		return model.getTexture().width;
	}

	@Override
	public float getHeight()
	{
		return model.getTexture().height;
	}

	@Override
	void onDraw()
	{
		transform();
		shader.upload();
		model.draw();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		model.dispose();
		model = null;
	}
}
