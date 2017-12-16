package zyx.engine.components.screen;

import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.GameTexture;
import zyx.game.controls.textures.TextureManager;

public class Image extends DisplayObject
{

	private ScreenModel model;
	
	private final float originalWidth;
	private final float originalHeight;
	
	public Image(String texture)
	{
		GameTexture gameTexture = TextureManager.getTexture(texture);
		model = new ScreenModel(gameTexture);
		
		originalWidth = getWidth();
		originalHeight = getHeight();
	}
	
	@Override
	public float getWidth()
	{
		return model.getWidth() * scale.x;
	}

	@Override
	public float getHeight()
	{
		return model.getHeight() * scale.y;
	}
	
	@Override
	public void setWidth(float value)
	{
		scale.x = value / originalWidth;
	}
	
	@Override
	public void setHeight(float value)
	{
		scale.y = value / originalHeight;
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
