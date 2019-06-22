package zyx.game.joining;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class PlayerJoinGameResponse extends BaseNetworkResponse<Integer>
{

	public PlayerJoinGameResponse()
	{
		super(NetworkCommands.PLAYER_JOINED_GAME);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		return data.getInteger("id");
	}

}
