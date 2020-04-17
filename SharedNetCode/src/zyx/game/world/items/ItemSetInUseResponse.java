package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;
import zyx.game.world.items.data.ItemChangedData;

public class ItemSetInUseResponse extends BaseNetworkResponse<ItemChangedData>
{

	private static final ItemChangedData OUT = ItemChangedData.INSTANCE;

	public ItemSetInUseResponse()
	{
		super(NetworkCommands.ITEM_SET_IN_USE);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.inUse = data.getBoolean(IN_USE);

		return OUT;
	}

}
