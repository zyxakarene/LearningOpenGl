package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateRequest extends BaseNetworkRequest
{
	public ItemCreateRequest()
	{
		super(NetworkCommands.ITEM_CREATE);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		int itemType = (int) params[1];
		int containerID = (int) params[2];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(ITEM_TYPE, itemType);
		data.addInteger(CONTAINER_ID, containerID);
	}

}
