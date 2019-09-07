package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;
import zyx.game.world.items.data.ItemChangedData;

public class ItemDropOnFloorResponse extends BaseNetworkResponse<ItemChangedData>
{
	private static final ItemChangedData OUT = ItemChangedData.INSTANCE;

	public ItemDropOnFloorResponse()
	{
		super(NetworkCommands.ITEM_PUT_ON_FOOR);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.ownerId = data.getInteger(CONTAINER_ID);
		OUT.position.x = data.getFloat(X);
		OUT.position.y = data.getFloat(Y);
		OUT.position.z = data.getFloat(Z);
		
		return OUT;
	}

}
