package zyx.game.world.items.data;

public class ItemChangedData
{
	public static final ItemChangedData INSTANCE = new ItemChangedData();
	
	public int itemId;
	public int ownerId;
	public String dishType;
	public int typeId;

	private ItemChangedData()
	{
	}
}
