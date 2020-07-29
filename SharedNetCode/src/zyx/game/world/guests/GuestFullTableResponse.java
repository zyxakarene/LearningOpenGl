package zyx.game.world.guests;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;
import zyx.net.io.responses.BaseNetworkResponse;

public class GuestFullTableResponse extends BaseNetworkResponse<Integer>
{
	public GuestFullTableResponse()
	{
		super(NetworkCommands.GUEST_FULL_TABLE);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int guestId = data.getInteger(GUEST_ID);

		return guestId;
	}

}
