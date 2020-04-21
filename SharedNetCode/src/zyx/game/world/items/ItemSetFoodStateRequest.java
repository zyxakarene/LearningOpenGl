package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemSetFoodStateRequest extends BaseNetworkRequest
{
	public ItemSetFoodStateRequest()
	{
		super(NetworkCommands.ITEM_SET_FOOD_STATE);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		int stateId = (int) params[1];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(FOOD_STATE, stateId);
	}
}
