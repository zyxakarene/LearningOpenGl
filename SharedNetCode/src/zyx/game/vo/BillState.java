package zyx.game.vo;

public enum BillState
{
	DEFAULT(1);
	
	private static final BillState[] values = values();
	
	public final int id;

	private BillState(int id)
	{
		this.id = id;
	}
	
	public static BillState getFromId(int id)
	{
		for (BillState dish : values)
		{
			if (dish.id == id)
			{
				return dish;
			}
		}
		
		String msg = String.format("No such DishType ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
}
