package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.game.world.items.data.ItemChangedData;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateOrdersResponse extends ItemCreateResponse
{

	public ItemCreateOrdersResponse()
	{
		super(NetworkCommands.ITEM_CREATE_ORDER);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		ItemChangedData out = super.onMessageRecieved(data);

		out.dishType = data.getString(DISH_TYPE);

		return out;
	}

}
