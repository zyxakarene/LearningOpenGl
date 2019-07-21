package zyx.game.login;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.login.LoginConstants.*;

public class AuthenticateRequest extends BaseNetworkRequest
{
	
	public AuthenticateRequest()
	{
		super(NetworkCommands.AUTHENTICATE);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		String name = (String) params[0];
		int id = (int) params[1];
		
		data.addString(NAME, name);
		data.addInteger(ID, id);
	}

}
