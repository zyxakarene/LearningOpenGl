package zyx.game.components.screen;

import zyx.opengl.models.implementations.ScreenModel;

public class Image extends DisplayObject
{

	private ScreenModel model;
	
	public Image(String texture)
	{
		model = new ScreenModel(texture);
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
	public void draw()
	{
		model.transform(position, rotation, scale);
		model.draw();
	}
}
