package zyx.server.world.humanoids.handheld.food;

import zyx.game.vo.DishType;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;

public class DishRecipeItem extends HandheldItem
{

	public final DishType dish;

	public DishRecipeItem(DishType dish)
	{
		super(HandheldItemType.DISH_RECIPE, true);
		
		this.dish = dish;
	}

	@Override
	public void process()
	{
	}

}
