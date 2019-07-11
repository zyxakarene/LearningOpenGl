package zyx.game.ping;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;

public class PingResponse extends BaseNetworkResponse<Integer>
{
	public PingResponse()
	{
		super(NetworkCommands.PING);
	}
	
	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		//System.out.println("Received a ping!!");
		return getSenderUniqueId();
	}

}
