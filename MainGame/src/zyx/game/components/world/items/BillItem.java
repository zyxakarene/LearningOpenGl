package zyx.game.components.world.items;

import zyx.game.vo.HandheldItemType;

public class BillItem extends GameItem
{
	
	public BillItem()
	{
		super(HandheldItemType.BILL);
	}

	@Override
	protected String getItemResource()
	{
		return "mesh.furniture.bill";
	}
}
