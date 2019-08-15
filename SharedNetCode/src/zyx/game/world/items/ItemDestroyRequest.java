package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemDestroyRequest extends BaseNetworkRequest
{
	public ItemDestroyRequest()
	{
		super(NetworkCommands.ITEM_DESTROY);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		
		data.addInteger(ITEM_ID, itemID);
	}

}
