package zyx.game.world.player;

import zyx.game.vo.DishType;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerEnterOrderResponse extends BaseNetworkResponse<DishType>
{

	public PlayerEnterOrderResponse()
	{
		super(NetworkCommands.PLAYER_ENTER_ORDER);
	}

	@Override
	protected DishType onMessageRecieved(ReadableDataObject data)
	{
		int dishTypeId = data.getInteger(DISH_TYPE);
		DishType result = DishType.getFromId(dishTypeId);
		
		return result;
	}

}
