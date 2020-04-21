package zyx.game.world.items.data;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.game.vo.HandheldItemType;

public class ItemChangedData
{
	public static final ItemChangedData INSTANCE = new ItemChangedData();
	
	public int itemId;
	public int ownerId;
	public boolean inUse;
	public DishType dishType;
	public FoodState foodState;
	public HandheldItemType type;
	public Vector3f position = new Vector3f();

	private ItemChangedData()
	{
	}
}
