package zyx.game.ping;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;

public class PingResponse extends BaseNetworkResponse<Integer>
{

	private static String ID = "id";

	public PingResponse()
	{
		super(NetworkCommands.PING);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		
		int id = data.getInteger(ID);
//		System.out.println(id + " Received a ping!!");
		return id;
	}

}
