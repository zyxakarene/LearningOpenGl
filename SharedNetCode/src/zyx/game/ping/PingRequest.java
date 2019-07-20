package zyx.game.ping;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;

public class PingRequest extends BaseNetworkRequest
{
	
	public PingRequest()
	{
		super(NetworkCommands.PING);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
//		System.out.println("Sending ping!");
	}

}
