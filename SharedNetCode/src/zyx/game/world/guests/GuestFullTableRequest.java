package zyx.game.world.guests;

import zyx.game.world.items.ItemCreateRequest;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;

public class GuestFullTableRequest extends ItemCreateRequest
{
	public GuestFullTableRequest()
	{
		super(NetworkCommands.GUEST_FULL_TABLE);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int guestId = (int) params[0];		
		data.addInteger(GUEST_ID, guestId);
	}
}
