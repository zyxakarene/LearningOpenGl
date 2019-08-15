package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;
import zyx.game.world.items.data.ItemChangedData;

public class ItemCreateResponse extends BaseNetworkResponse<ItemChangedData>
{
	private static final ItemChangedData OUT = ItemChangedData.INSTANCE;

	public ItemCreateResponse()
	{
		super(NetworkCommands.ITEM_CREATE);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.ownerId = data.getInteger(CONTAINER_ID);

		return OUT;
	}

}
