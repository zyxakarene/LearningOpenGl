package zyx.game.joining.data;

import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;

public class ItemSetupData
{

	public static final int MAX_COUNT = 30;
	public static final ItemSetupData INSTANCE = new ItemSetupData();

	public int itemCount;
	
	public final int[] ids;
	public final int[] ownerIds;
	public final HandheldItemType[] types;
	public final DishType[] dishTypes;
	public final Boolean[] dishSpoiled;

	private ItemSetupData()
	{
		itemCount = 0;
		
		ids = new int[MAX_COUNT];
		ownerIds = new int[MAX_COUNT];
		types = new HandheldItemType[MAX_COUNT];
		dishTypes = new DishType[MAX_COUNT];
		dishSpoiled = new Boolean[MAX_COUNT];
		
		for (int i = 0; i < MAX_COUNT; i++)
		{
			ids[i] = 0;
			ownerIds[i] = 0;
			types[i] = HandheldItemType.INGREDIENTS;
			dishTypes[i] = DishType.STEAK;
			dishSpoiled[i] = false;
		}
	}
}
