package zyx.game.world.guests;

import zyx.game.world.items.ItemCreateRequest;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;

public class GuestPayRequest extends ItemCreateRequest
{
	public GuestPayRequest()
	{
		super(NetworkCommands.GUEST_PAY);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int[] guestIds = (int[]) params[0];
		int[] payAmounts = (int[]) params[1];
		
		data.addIntegerArray(GUEST_ID, guestIds);
		data.addIntegerArray(PAY_AMOUNTS, payAmounts);
	}
}
