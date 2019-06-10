package auth;

import zyx.net.data.ReadableDataObject;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class LoginResponse extends BaseNetworkResponse<String>
{
	private static int idCounter = 0;

	public LoginResponse()
	{
		super(NetworkCommands.LOGIN);
	}

	@Override
	protected String onMessageRecieved(ReadableDataObject data)
	{
		String playerName = data.getString("name");
				
		idCounter++;
		
		WriteableDataObject loginData = new WriteableDataObject();
		loginData.addInteger("id", idCounter);
		sendReply(NetworkCommands.AUTH, loginData);
		
		return playerName;
	}
}
