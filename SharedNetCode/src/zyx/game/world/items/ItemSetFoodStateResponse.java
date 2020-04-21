package zyx.game.world.items;

import zyx.game.vo.FoodState;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;
import zyx.game.world.items.data.ItemChangedData;

public class ItemSetFoodStateResponse extends BaseNetworkResponse<ItemChangedData>
{

	private static final ItemChangedData OUT = ItemChangedData.INSTANCE;

	public ItemSetFoodStateResponse()
	{
		super(NetworkCommands.ITEM_SET_FOOD_STATE);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		int type = data.getInteger(FOOD_STATE);
		
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.foodState = FoodState.getFromId(type);

		return OUT;
	}

}
