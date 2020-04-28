package zyx.game.components.screen.radial.food;

import zyx.game.components.screen.radial.RadialButtonClickAdaptor;
import zyx.game.components.screen.radial.RadialMenu;
import zyx.game.vo.DishType;

public class FoodRadiallMenu extends RadialMenu
{
	private FoodItemWrapper[] items;
	
	@Override
	protected void onPreInitialize()
	{
		DishType[] dishes = DishType.values();
		int len = dishes.length;
		items = new FoodItemWrapper[len];
		for (int i = 0; i < len; i++)
		{
			items[i] = new FoodItemWrapper(dishes[i]);
		}
		
		super.onPreInitialize();
	}

	@Override
	protected FoodItemWrapper[] getAllPossibilities()
	{
		return items;
	}

	@Override
	protected RadialButtonClickAdaptor getClickAdaptor()
	{
		return new FoodRadialButtonClickAdaptor();
	}

}
