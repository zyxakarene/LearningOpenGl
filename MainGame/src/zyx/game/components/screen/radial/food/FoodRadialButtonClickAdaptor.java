package zyx.game.components.screen.radial.food;

import zyx.game.components.screen.radial.*;
import java.util.HashMap;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.network.services.PlayerService;
import zyx.game.vo.DishType;

public class FoodRadialButtonClickAdaptor extends RadialButtonClickAdaptor
{
	@Override
	protected void addCallbacks(HashMap<Integer, ICallback<RadialMenuItemRenderer>> callbackMap)
	{
		DishType[] dishes = DishType.values();
		for (DishType dish : dishes)
		{
			callbackMap.put(dish.id, this::onFoodClicked);
		}
	}
	
	private void onFoodClicked(RadialMenuItemRenderer renderer)
	{
		closeRadial();
		DishType dish = DishType.getFromId(renderer.data.getUniqueId());
		PlayerService.enterOrder(dish);
	}
}
