package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;

public class Table extends BaseFurnitureItem<SimpleMesh>
{

	public Table()
	{
		super(false);
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		item.setPosition(true, 0, 43, 0);
		item.setRotation(0, 0, 0);
		addChild(item);
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		removeChild(item);
	}
	
	@Override
	protected String getResource()
	{
		return "mesh.furniture.table";
	}

}
