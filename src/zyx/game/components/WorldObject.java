package zyx.game.components;

import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZmfLoader;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{
	private final WorldModel model;

	public WorldObject()
	{
		model = ZmfLoader.loadFromZmf("mesh2.zmf");
	}

	@Override
	public void draw()
	{
		model.transform(position, rotation, scale);
		model.draw();
	}
}
