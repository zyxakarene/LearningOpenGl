package zyx.game.joining;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class PlayerLeaveGameResponse extends BaseNetworkResponse<Integer>
{

	public PlayerLeaveGameResponse()
	{
		super(NetworkCommands.PLAYER_LEFT_GAME);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		return data.getInteger("id");
	}

}
