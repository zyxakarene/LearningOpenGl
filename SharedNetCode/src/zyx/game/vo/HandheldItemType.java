package zyx.game.vo;

public enum HandheldItemType
{
	//Food
	FOOD(1),
	
	//Players & Guests
	BILL(2);
	
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
