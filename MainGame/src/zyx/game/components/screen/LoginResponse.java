package zyx.game.components.screen;

import java.net.InetAddress;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.IResponse;

public class LoginResponse implements IResponse
{

	@Override
	public void onMessage(ReadableDataObject data, InetAddress senderHost, int senderPort)
	{
		System.out.println("Got loginResponse!");
		System.out.println(data.getInteger("userId"));
	}

}
