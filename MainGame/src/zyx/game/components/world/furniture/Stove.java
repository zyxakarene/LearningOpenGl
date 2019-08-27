package zyx.game.components.world.furniture;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.items.GameItem;

public class Stove extends BaseFurnitureItem<SimpleMesh>
{

	public Stove()
	{
		super(false);
	}

	@Override
	protected void onGotItem(GameItem item)
	{
		view.addChildAsAttachment(item, "Stove_plate");
	}

	@Override
	protected void onLostItem(GameItem item)
	{
		view.removeChildAsAttachment(item);
		
		item.setPosition(true, 0, 0, 0);
		item.setRotation(0, 0, 0);
	}

	@Override
	protected String getResource()
	{
		return "mesh.furniture.stove";
	}
}
