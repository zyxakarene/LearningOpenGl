package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemSpoilFoodRequest extends BaseNetworkRequest
{
	public ItemSpoilFoodRequest()
	{
		super(NetworkCommands.ITEM_SPOIL_FOOD);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		data.addInteger(ITEM_ID, itemID);
	}
}
