package zyx.game.world.items;

import zyx.game.vo.DishType;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateFoodRequest extends ItemCreateRequest
{
	public ItemCreateFoodRequest()
	{
		super(NetworkCommands.ITEM_CREATE_FOOD);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		super.getDataObject(data, params);
		
		DishType dish = (DishType) params[2];
		
		data.addInteger(DISH_TYPE, dish.id);
	}
}
