package zyx.game.world.items.data;

import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;

public class ItemChangedData
{
	public static final ItemChangedData INSTANCE = new ItemChangedData();
	
	public int itemId;
	public int ownerId;
	public DishType dishType;
	public HandheldItemType type;

	private ItemChangedData()
	{
	}
}
