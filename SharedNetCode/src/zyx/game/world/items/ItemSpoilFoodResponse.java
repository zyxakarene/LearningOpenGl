package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemSpoilFoodResponse extends BaseNetworkResponse<Integer>
{

	public ItemSpoilFoodResponse()
	{
		super(NetworkCommands.ITEM_SPOIL_FOOD);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int id = data.getInteger(ITEM_ID);
		return id;
	}

}
