package zyx.game.world.items;

import org.lwjgl.util.vector.Vector3f;
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
		Vector3f position = (Vector3f) params[2];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(CONTAINER_ID, containerID);
		
		data.addFloat(X, position.x);
		data.addFloat(Y, position.y);
		data.addFloat(Z, position.z);
	}

}
