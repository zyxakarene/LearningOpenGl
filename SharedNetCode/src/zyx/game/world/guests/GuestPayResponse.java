package zyx.game.world.guests;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;
import zyx.game.world.guests.data.GuestPayData;
import zyx.net.io.responses.BaseNetworkResponse;

public class GuestPayResponse extends BaseNetworkResponse<GuestPayData>
{
	private static final GuestPayData OUT = GuestPayData.INSTANCE;
	
	public GuestPayResponse()
	{
		super(NetworkCommands.GUEST_PAY);
	}

	@Override
	protected GuestPayData onMessageRecieved(ReadableDataObject data)
	{
		OUT.characterIds = data.getIntegerArray(GUEST_ID);
		OUT.payAmounts = data.getIntegerArray(PAY_AMOUNTS);

		return OUT;
	}

}
