package zyx.game.vo;

public enum HandheldItemType
{
	//Food
	DISH_RECIPE(1),
	INGREDIENTS(2),
	POT(3),
	FOOD(4),
	DIRTY_PLATE(5),
	
	//Players & Guests
	BILL(6);
	
	private static final HandheldItemType[] values = values();

	public final int id;

	private HandheldItemType(int id)
	{
		this.id = id;
	}
	
	public static HandheldItemType getFromId(int id)
	{
		for (HandheldItemType type : values)
		{
			if (type.id == id)
			{
				return type;
			}
		}
		
		String msg = String.format("No such HandheldItemType ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
}
