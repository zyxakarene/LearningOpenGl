package zyx.server.world.humanoids.handheld.food;

import zyx.game.vo.DishType;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;
import zyx.server.controller.services.ItemService;
import zyx.server.world.wallet.MoneyJar;

public class FoodItem extends HandheldItem
{
	
	private static final int FOOD_LIFESPAN_MS = 10000; //10 seconds
	
	public final DishType dish;
	
	private int lifeSpan;
	private boolean spoiled;
	
	public FoodItem(DishType resultDish)
	{
		super(HandheldItemType.INGREDIENTS, false);
		lifeSpan = FOOD_LIFESPAN_MS;
		spoiled = false;
		dish = resultDish;
		
		MoneyJar.getInstance().addCost(dish.productionCost);
	}

	@Override
	public void process()
	{
		switch (type)
		{
			case INGREDIENTS:
			{
				type = HandheldItemType.POT;
				break;
			}
			case POT:
			{
				type = HandheldItemType.FOOD;
				break;
			}
			case FOOD:
			{
				type = HandheldItemType.DIRTY_PLATE;
				break;
			}
		}
		
		ItemService.setType(this, type);
	}

	public boolean isEdible()
	{
		return !spoiled && type == HandheldItemType.FOOD;
	}

	public boolean isSpoiled()
	{
		return spoiled;
	}
	
	public boolean cleanableByCleaner()
	{
		return !inUse && 
						(
							(type == HandheldItemType.DIRTY_PLATE)
							|| 
							(spoiled && type == HandheldItemType.FOOD)
						);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (!spoiled && type == HandheldItemType.FOOD)
		{
			lifeSpan -= elapsedTime;
			
			if (lifeSpan <= 0)
			{
				ItemService.spoilFood(this);
				spoiled = true;
			}
		}
	}
	
	@Override
	public String getVisualName()
	{
		String typeName = super.getVisualName();
		
		if (type == HandheldItemType.FOOD)
		{
			return typeName + String.format("(%s)", spoiled ? "rotten" : "fresh");
		}
		else
		{
			return typeName;
		}
	}
}
