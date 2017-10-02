package zyx.game.components.screen;

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
	protected final void draw()
	{
		transform();
		shader.upload();
		model.draw();
	}
}
