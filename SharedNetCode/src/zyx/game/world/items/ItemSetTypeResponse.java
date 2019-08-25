package zyx.game.world.items;

import zyx.game.vo.HandheldItemType;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;
import zyx.game.world.items.data.ItemChangedData;

public class ItemSetTypeResponse extends BaseNetworkResponse<ItemChangedData>
{

	private static final ItemChangedData OUT = ItemChangedData.INSTANCE;

	public ItemSetTypeResponse()
	{
		super(NetworkCommands.ITEM_SET_TYPE);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		String type = data.getString(ITEM_TYPE);
		
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.type = HandheldItemType.valueOf(type);

		return OUT;
	}

}
