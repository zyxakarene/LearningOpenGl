package zyx.game.components.screen.radial.food;

import zyx.game.components.screen.radial.IRadialMenuOption;
import zyx.game.vo.DishType;

public class FoodItemWrapper implements IRadialMenuOption
{
	private DishType dish;

	public FoodItemWrapper(DishType dish)
	{
		this.dish = dish;
	}
	
	@Override
	public String getText()
	{
		return dish.name;
	}

	@Override
	public String getIconResource()
	{
		return String.format("icon_%s", dish.viewId);
	}

	@Override
	public int getUniqueId()
	{
		return dish.id;
	}

}
