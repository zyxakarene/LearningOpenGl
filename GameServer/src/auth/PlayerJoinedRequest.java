package auth;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class PlayerJoinedRequest extends BaseNetworkRequest
{

	public PlayerJoinedRequest()
	{
		super(NetworkCommands.PLAYER_JOINED_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		int id = (int) params[0];
		
		data.addInteger("id", id);
	}

}
