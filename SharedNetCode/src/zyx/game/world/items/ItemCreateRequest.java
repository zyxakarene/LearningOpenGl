package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateRequest extends BaseNetworkRequest
{
	public ItemCreateRequest(String command)
	{
		super(command);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		int containerID = (int) params[1];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(CONTAINER_ID, containerID);
	}

}
