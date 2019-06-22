package zyx.game.joining;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class PlayerJoinGameRequest extends BaseNetworkRequest
{

	private static final String PLAYER_ID = "id";

	public PlayerJoinGameRequest()
	{
		super(NetworkCommands.PLAYER_JOINED_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		int id = (int) params[0];
		
		data.addInteger(PLAYER_ID, id);
	}

}
