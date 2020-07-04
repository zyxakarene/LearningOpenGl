package zyx.game.vo;

public enum FurnitureType
{
	FLOOR(1),
	ORDER_MACHINE(2),
	CHAIR(3),
	DINNER_TABLE(4),
	MONITOR(5),
	FRIDGE(6),
	STOVE(7),
	FOOD_TABLE(8),
	DISHWASHER(9),
	GUEST_EXIT(10),
	FLOOR_ITEM(11);

	private static final FurnitureType[] values = values();

	public final int id;

	private FurnitureType(int id)
	{
		this.id = id;
	}
	
	public static FurnitureType getFromId(int id)
	{
		for (FurnitureType furniture : values)
		{
			if (furniture.id == id)
			{
				return furniture;
			}
		}
		
		String msg = String.format("No such FurnitureType ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
}
