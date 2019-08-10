package zyx.server.world.humanoids.handheld.food;

import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemType;

public class DishRecipeItem extends HandheldItem
{

	public final DishType dish;

	public DishRecipeItem(DishType dish)
	{
		super(HandheldItemType.DISH_RECIPE);
		
		this.dish = dish;
	}

	@Override
	public void process()
	{
	}

}
