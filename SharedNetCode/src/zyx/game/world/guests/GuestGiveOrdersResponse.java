package zyx.game.world.guests;

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
		OUT.characterId = data.getInteger(GUEST_ID);
		OUT.dishTypeId = data.getInteger(DISH_TYPE);

		return OUT;
	}

}
