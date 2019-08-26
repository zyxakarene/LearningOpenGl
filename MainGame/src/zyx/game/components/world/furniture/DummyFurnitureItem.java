package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;

public class DummyFurnitureItem extends BaseFurnitureItem<SimpleMesh>
{

	public DummyFurnitureItem()
	{
		super(false);
	}

	@Override
	protected String getResource()
	{
		return "mesh.simple.box";
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		addChild(item);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		removeChild(item);
	}
}
