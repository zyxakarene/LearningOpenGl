package zyx.game.world.guests;

import zyx.game.world.items.ItemCreateRequest;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;

public class GuestGiveOrdersRequest extends ItemCreateRequest
{
	public GuestGiveOrdersRequest()
	{
		super(NetworkCommands.GUEST_GIVE_ORDER);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int guestId = (int) params[0];
		int dishTypeId = (int) params[1];
		
		data.addInteger(GUEST_ID, guestId);
		data.addInteger(DISH_TYPE, dishTypeId);
	}
}
