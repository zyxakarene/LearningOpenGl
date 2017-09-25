package zyx.game.components.screen;

import zyx.game.components.GameObject;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.utils.interfaces.IDrawable;

public class DisplayObject extends GameObject implements IDrawable
{
	private final ScreenModel model;

	public DisplayObject()
	{
		model = new ScreenModel();
	}

	@Override
	public void draw()
	{
		model.transform(position, rotation, scale);
		model.draw();
	}
}
