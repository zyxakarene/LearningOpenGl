package zyx.game.world.guests;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;
import zyx.game.world.guests.data.GuestGrabData;
import zyx.net.io.responses.BaseNetworkResponse;

public class GuestGrabFoodResponse extends BaseNetworkResponse<GuestGrabData>
{

	private static final GuestGrabData OUT = GuestGrabData.INSTANCE;

	public GuestGrabFoodResponse()
	{
		super(NetworkCommands.GUEST_GRAB_FOOD);
	}

	@Override
	protected GuestGrabData onMessageRecieved(ReadableDataObject data)
	{
		OUT.characterId = data.getInteger(GUEST_ID);
		OUT.foodId = data.getInteger(FOOD_ID);
		OUT.correct = data.getBoolean(CORRECT_FOOD);

		return OUT;
	}

}
