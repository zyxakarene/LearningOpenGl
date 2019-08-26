package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;

public class Table extends BaseFurnitureItem<SimpleMesh>
{

	public Table(boolean animated)
	{
		super(animated);
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
	
	@Override
	protected String getResource()
	{
		return "mesh.simple.box";
	}

}
