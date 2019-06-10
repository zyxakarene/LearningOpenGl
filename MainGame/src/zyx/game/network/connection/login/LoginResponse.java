package zyx.game.network.connection.login;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;

public class LoginResponse extends BaseNetworkResponse<Integer>
{

	public LoginResponse()
	{
		super(NetworkCommands.AUTH);
	}
	
	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		Integer userId = data.getInteger("id");
		
		return userId;
	}

}
