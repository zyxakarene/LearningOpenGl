package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;

public class DummyFurnitureItem extends NpcFurniture<SimpleMesh>
{

	public DummyFurnitureItem()
	{
		super(false);
		
		setScale(0.02f, 0.02f, 0.02f);
	}

	@Override
	protected String getResource()
	{
		return "mesh.box";
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
