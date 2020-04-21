package zyx.game.vo;

public enum FoodState
{
	RECIPE(1),
	INGREDIENTS(2),
	POT(3),
	FOOD(4),
	DIRTY_PLATE(5);
	
	
	private static final FoodState[] values = values();
	
	public final int id;

	private FoodState(int id)
	{
		this.id = id;
	}
	
	public static FoodState getFromId(int id)
	{
		for (FoodState dish : values)
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
