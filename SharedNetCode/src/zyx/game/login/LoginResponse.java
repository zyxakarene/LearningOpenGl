package zyx.game.login;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.login.LoginConstants.*;
import zyx.game.vo.LoginData;

public class LoginResponse extends BaseNetworkResponse<LoginData>
{
	private static final LoginData OUT = new LoginData();

	public LoginResponse()
	{
		super(NetworkCommands.LOGIN);
	}
	
	@Override
	protected LoginData onMessageRecieved(ReadableDataObject data)
	{
		OUT.name = data.getString(NAME);
		OUT.connection = asConnectionData();
		
		return OUT;
	}

}
