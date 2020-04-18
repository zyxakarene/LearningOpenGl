package zyx.game.ping;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;

public class PingRequest extends BaseNetworkRequest
{
	private static String ID = "id";
	
	public PingRequest()
	{
		super(NetworkCommands.PING);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int id = (int) params[0];
		data.addInteger(ID, id);
		
//		System.out.println(id + " Sending ping!");
	}

}
