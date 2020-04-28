package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerEnterOrderResponse extends BaseNetworkResponse<Integer>
{

	public PlayerEnterOrderResponse()
	{
		super(NetworkCommands.PLAYER_ENTER_ORDER);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int dishTypeId = data.getInteger(DISH_TYPE);
		return dishTypeId;
	}

}
