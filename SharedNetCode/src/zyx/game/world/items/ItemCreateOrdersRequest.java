package zyx.game.world.items;

import zyx.game.vo.DishType;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateOrdersRequest extends ItemCreateRequest
{
	public ItemCreateOrdersRequest()
	{
		super(NetworkCommands.ITEM_CREATE_ORDER);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		super.getDataObject(data, params);
		
		DishType dishType = (DishType) params[2];
		
		data.addString(DISH_TYPE, dishType.toString());
	}
}
