package zyx.game.login;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.login.LoginConstants.*;
import zyx.game.vo.AuthenticationData;

public class AuthenticateResponse extends BaseNetworkResponse<AuthenticationData>
{
	private static final AuthenticationData OUT = new AuthenticationData();

	public AuthenticateResponse()
	{
		super(NetworkCommands.AUTHENTICATE);
	}
	
	@Override
	protected AuthenticationData onMessageRecieved(ReadableDataObject data)
	{
		OUT.name = data.getString(NAME);
		OUT.id = data.getInteger(ID);
		
		return OUT;
	}

}
