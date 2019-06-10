package zyx.game.network.connection.login;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;

public class LoginRequest extends BaseNetworkRequest
{
	private static final String NAME = "name";
	
	public LoginRequest()
	{
		super(NetworkCommands.LOGIN);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		String myName = (String) params[0];
		
		data.addString(NAME, myName);
	}

}
