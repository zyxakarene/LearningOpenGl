package zyx.game.world.items;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.game.world.items.data.ItemChangedData;
import static zyx.game.world.items.ItemConstants.*;

public class ItemCreateFoodResponse extends ItemCreateResponse
{

	public ItemCreateFoodResponse()
	{
		super(NetworkCommands.ITEM_CREATE_FOOD);
	}

	@Override
	protected ItemChangedData onMessageRecieved(ReadableDataObject data)
	{
		ItemChangedData out = super.onMessageRecieved(data);

		out.dishTypeId = data.getInteger(DISH_TYPE);

		return out;
	}

}
