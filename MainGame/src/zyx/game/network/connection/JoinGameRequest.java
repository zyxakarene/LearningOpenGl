package zyx.game.network.connection;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class JoinGameRequest extends BaseNetworkRequest
{

	private static final String PLAYER_ID = "id";

	public JoinGameRequest()
	{
		super(NetworkCommands.JOIN_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		int id = (int) params[0];
		
		data.addInteger(PLAYER_ID, id);
	}

}
