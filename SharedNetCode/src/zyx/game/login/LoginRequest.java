package zyx.game.login;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.login.LoginConstants.*;

public class LoginRequest extends BaseNetworkRequest
{
	
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
