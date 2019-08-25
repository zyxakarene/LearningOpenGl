package zyx.game.components.world.items;

import zyx.game.vo.HandheldItemType;

public class OrderItem extends GameItem
{
	
	public OrderItem()
	{
		super(HandheldItemType.ORDERS);
	}

	@Override
	protected String getItemResource()
	{
		return "mesh.furniture.order";
	}
}
