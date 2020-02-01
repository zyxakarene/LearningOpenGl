package zyx.game.vo;

public enum DishType
{
	STEAK(1, 15, 55);
//	SOUP(2, 10, 25),
//	SALAD(3, 10, 15),
//	FISH(4, 30, 80);
	
	
	private static final DishType[] values = values();
	
	public final int id;
	public final int productionCost;
	public final int sellValue;

	private DishType(int id, int productionCost, int sellValue)
	{
		this.id = id;
		this.productionCost = productionCost;
		this.sellValue = sellValue;
	}
	
	public static DishType getFromId(int id)
	{
		for (DishType dish : values)
		{
			if (dish.id == id)
			{
				return dish;
			}
		}
		
		String msg = String.format("No such DishType ID: %s", id);
		throw new IllegalArgumentException(msg);
	}
	
	public static DishType getRandomDish()
	{
        return values[(int) (Math.random() * values.length)];
    }
}
