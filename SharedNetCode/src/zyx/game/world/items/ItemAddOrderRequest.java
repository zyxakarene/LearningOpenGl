package zyx.game.world.items;

import zyx.game.vo.DishType;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemAddOrderRequest extends BaseNetworkRequest
{
	public ItemAddOrderRequest()
	{
		super(NetworkCommands.ITEM_ADD_ORDER);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int orderID = (int) params[0];
		DishType dishType = (DishType) params[1];
		
		data.addInteger(ITEM_ID, orderID);
		data.addString(DISH_TYPE, dishType.toString());
	}
}
