package zyx.game.world.items;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemDropOnFloorRequest extends BaseNetworkRequest
{
	public ItemDropOnFloorRequest()
	{
		super(NetworkCommands.ITEM_PUT_ON_FOOR);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		int containerID = (int) params[1];
		float x = (float) params[2];
		float y = (float) params[3];
		float z = (float) params[4];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(CONTAINER_ID, containerID);
		
		data.addFloat(X, x);
		data.addFloat(Y, y);
		data.addFloat(Z, z);
	}

}
