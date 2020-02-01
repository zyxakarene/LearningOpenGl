package zyx.game.world.guests;

import zyx.game.vo.DishType;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;
import zyx.game.world.guests.data.GuestOrderData;
import zyx.net.io.responses.BaseNetworkResponse;

public class GuestGiveOrdersResponse extends BaseNetworkResponse<GuestOrderData>
{
	private static final GuestOrderData OUT = GuestOrderData.INSTANCE;
	
	public GuestGiveOrdersResponse()
	{
		super(NetworkCommands.GUEST_GIVE_ORDER);
	}

	@Override
	protected GuestOrderData onMessageRecieved(ReadableDataObject data)
	{
		int dish = data.getInteger(DISH_TYPE);
		
		OUT.characterId = data.getInteger(GUEST_ID);
		OUT.dishType = DishType.getFromId(dish);

		return OUT;
	}

}
