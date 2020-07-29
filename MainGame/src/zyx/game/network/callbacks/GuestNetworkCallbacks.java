package zyx.game.network.callbacks;

import zyx.game.components.world.characters.GameCharacter;
import zyx.game.vo.DishType;
import zyx.game.world.guests.data.GuestGrabData;
import zyx.game.world.guests.data.GuestOrderData;
import zyx.game.world.guests.data.GuestPayData;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GuestNetworkCallbacks extends AbstractDinerNetworkCallbacks
{

	public GuestNetworkCallbacks()
	{
		registerCallback(NetworkCommands.GUEST_FULL_TABLE, (INetworkCallback<Integer>) this::onTableFull);
		registerCallback(NetworkCommands.GUEST_GIVE_ORDER, (INetworkCallback<GuestOrderData>) this::onGiveOrder);
		registerCallback(NetworkCommands.GUEST_GRAB_FOOD, (INetworkCallback<GuestGrabData>) this::onGrabFood);
		registerCallback(NetworkCommands.GUEST_NO_ORDERS, (INetworkCallback<Integer>) this::onNoOrderGiven);
		registerCallback(NetworkCommands.GUEST_PAY, (INetworkCallback<GuestPayData>) this::onPaid);
	}

	private void onTableFull(Integer characterId)
	{
		GameCharacter guest = characterMap.get(characterId);
		
		if (guest != null)
		{
			Print.out("Guest", guest, "lets you know that their table is all full");
		}
	}
	
	private void onPaid(GuestPayData payData)
	{
		int count = payData.characterIds.length;
		for (int i = 0; i < count; i++)
		{
			int characterId = payData.characterIds[i];
			int payAmount = payData.payAmounts[i];
			
			GameCharacter guest = characterMap.get(characterId);
			if (guest != null)
			{
				Print.out("Guest", guest, "paid", payAmount);
			}
		}
	}
	
	private void onNoOrderGiven(Integer characterId)
	{
		GameCharacter guest = characterMap.get(characterId);
		
		if (guest != null)
		{
			Print.out("Guest", guest, "mentioned how their table have not yet ordered anything");
		}
	}
	
	private void onGrabFood(GuestGrabData data)
	{
		GameCharacter guest = characterMap.get(data.characterId);
		
		if (guest != null)
		{
			Print.out("Guest", guest, "grabbed the food", data.foodId, "Was it correct food:", data.correct);
		}
	}
	private void onGiveOrder(GuestOrderData data)
	{
		GameCharacter guest = characterMap.get(data.characterId);

		if (guest != null)
		{
//			guest.TellDishAnimation();
			Print.out("Guest", guest, "wanted the dish", DishType.getFromId(data.dishTypeId));
		}
	}
}
