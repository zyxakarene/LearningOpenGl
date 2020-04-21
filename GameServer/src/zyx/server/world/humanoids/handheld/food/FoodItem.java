package zyx.server.world.humanoids.handheld.food;

import zyx.game.vo.DishType;
import zyx.game.vo.FoodState;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;
import zyx.server.controller.services.ItemService;
import zyx.server.world.wallet.MoneyJar;

public class FoodItem extends HandheldItem
{
	
	private static final int FOOD_LIFESPAN_MS = 10000; //10 seconds
	
	public final DishType dish;
	public FoodState state;
	
	private int lifeSpan;
	private boolean spoiled;
	
	public FoodItem(DishType resultDish)
	{
		super(HandheldItemType.FOOD, false);
		lifeSpan = FOOD_LIFESPAN_MS;
		spoiled = false;
		dish = resultDish;
		state = FoodState.RECIPE;
	}

	@Override
	public void process()
	{
		switch (state)
		{
			case RECIPE:
			{
				state = FoodState.INGREDIENTS;
				MoneyJar.getInstance().addCost(dish.productionCost);
				break;
			}
			case INGREDIENTS:
			{
				state = FoodState.POT;
				break;
			}
			case POT:
			{
				state = FoodState.FOOD;
				break;
			}
			case FOOD:
			{
				state = FoodState.DIRTY_PLATE;
				break;
			}
		}
		
		ItemService.setFoodState(this, state);
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
							(state == FoodState.DIRTY_PLATE)
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
