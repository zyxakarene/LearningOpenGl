package zyx.game.world.guests;

import zyx.game.world.items.ItemCreateRequest;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.guests.GuestsConstants.*;

public class GuestGrabFoodRequest extends ItemCreateRequest
{
	public GuestGrabFoodRequest()
	{
		super(NetworkCommands.GUEST_GRAB_FOOD);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int guestId = (int) params[0];
		int foodId = (int) params[1];
		boolean correctDish = (boolean) params[2];
		
		data.addInteger(GUEST_ID, guestId);
		data.addInteger(FOOD_ID, foodId);
		data.addBoolean(CORRECT_FOOD, correctDish);
	}
}
