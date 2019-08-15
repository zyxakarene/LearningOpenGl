package zyx.game.world.items.data;

public class ItemChangedData
{
	public static final ItemChangedData INSTANCE = new ItemChangedData();
	
	public int itemId;
	public int typeId;
	public int ownerId;

	private ItemChangedData()
	{
	}
}
