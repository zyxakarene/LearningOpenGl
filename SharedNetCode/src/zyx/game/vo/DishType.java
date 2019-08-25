package zyx.game.vo;

public enum DishType
{
	STEAK,
	SOUP,
	SALAD,
	FISH;
	
	
	private static final DishType[] values = values();
	
	public static DishType getRandomDish()
	{
        return values[(int) (Math.random() * values.length)];
    }
}
