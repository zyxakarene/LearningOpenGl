package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;
import zyx.game.world.player.data.PlayerRequestData;

public class PlayerEnterOrderResponse extends BaseNetworkResponse<Integer>
{

	private static final PlayerRequestData OUT = PlayerRequestData.INSTANCE;

	public PlayerEnterOrderResponse()
	{
		super(NetworkCommands.PLAYER_ENTER_ORDER);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int playerId = data.getInteger(PLAYER_ID);

		return playerId;
	}

}
