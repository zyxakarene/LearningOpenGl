package zyx.game.joining;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class PlayerLeaveGameRequest extends BaseNetworkRequest
{

	private static final String PLAYER_ID = "id";

	public PlayerLeaveGameRequest()
	{
		super(NetworkCommands.PLAYER_LEFT_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		int id = (int) params[0];
		
		data.addInteger(PLAYER_ID, id);
	}

}
