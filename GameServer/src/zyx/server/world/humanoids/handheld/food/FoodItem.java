package zyx.server.world.humanoids.handheld.food;

import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemType;

public class FoodItem extends HandheldItem
{
	
	private static final int FOOD_LIFESPAN_MS = 10000; //10 seconds
	
	public final DishType dish;
	
	private int lifeSpan;
	private boolean spoiled;
	
	public FoodItem(DishType resultDish)
	{
		super(HandheldItemType.INGREDIENTS);
		lifeSpan = FOOD_LIFESPAN_MS;
		spoiled = false;
		dish = resultDish;
	}

	@Override
	public void process()
	{
		lifeSpan = FOOD_LIFESPAN_MS;
		
		switch (type)
		{
			case INGREDIENTS:
				type = HandheldItemType.POT;
				break;
			case POT:
				type = HandheldItemType.FOOD;
				break;
			case FOOD:
				type = HandheldItemType.DIRTY_PLATE;
				break;
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (!spoiled && type != HandheldItemType.DIRTY_PLATE)
		{
			lifeSpan -= elapsedTime;
			
			if (lifeSpan <= 0)
			{
				spoiled = true;
			}
		}
	}
}
