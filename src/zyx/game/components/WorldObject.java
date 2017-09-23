package zyx.game.components;

import zyx.opengl.models.implementations.WorldModel;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{
	private final WorldModel model;

	public WorldObject()
	{
		model = new WorldModel();
		
		model.setScale(30);
	}

	@Override
	public void draw()
	{
		model.transform(position, rotation);
		model.draw();
	}
}
