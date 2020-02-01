package zyx.game.world.guests;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;
import zyx.net.io.responses.BaseNetworkResponse;

public class GuestNoOrdersResponse extends BaseNetworkResponse<Integer>
{
	public GuestNoOrdersResponse()
	{
		super(NetworkCommands.GUEST_NO_ORDERS);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int guestId = data.getInteger(GUEST_ID);

		return guestId;
	}

}
