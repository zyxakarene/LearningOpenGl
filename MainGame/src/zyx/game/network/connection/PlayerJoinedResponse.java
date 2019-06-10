package zyx.game.network.connection;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.utils.cheats.Print;

public class PlayerJoinedResponse extends BaseNetworkResponse<Integer>
{

	public PlayerJoinedResponse()
	{
		super(NetworkCommands.PLAYER_JOINED);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		Print.out(data);
		
		return data.getInteger("id");
	}

}
