package zyx.game.world.items.data;

import org.lwjgl.util.vector.Vector3f;

public class ItemChangedData
{
	public static final ItemChangedData INSTANCE = new ItemChangedData();
	
	public int itemId;
	public int ownerId;
	public boolean inUse;
	public int dishTypeId;
	public int foodStateId;
	public int typeId;
	public Vector3f position = new Vector3f();

	private ItemChangedData()
	{
	}
}
