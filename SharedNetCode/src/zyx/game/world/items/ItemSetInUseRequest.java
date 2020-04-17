package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemSetInUseRequest extends BaseNetworkRequest
{
	public ItemSetInUseRequest()
	{
		super(NetworkCommands.ITEM_SET_IN_USE);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		boolean inUse = (boolean) params[1];
		
		data.addInteger(ITEM_ID, itemID);
		data.addBoolean(IN_USE, inUse);
	}
}
