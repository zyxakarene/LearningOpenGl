package zyx.net.core;

import java.io.IOException;
import java.net.DatagramPacket;
import zyx.net.io.connections.ConnectionRequest;
import zyx.net.io.connections.ConnectionResponse;
import zyx.synchronizer.BaseRunner;

public class ConnectionRequestRunner extends BaseRunner<ConnectionRequest, ConnectionResponse>
{

	@Override
	protected ConnectionResponse handleEntry(ConnectionRequest entry)
	{
		byte[] byteData = entry.getData();
		DatagramPacket packet = new DatagramPacket(byteData, byteData.length, entry.host, entry.port);

		DebugNetworkList.addRequest(byteData);
		
		try
		{
			PersistentConnection.getInstance().send(packet);
		}
		catch (IOException ex)
		{
			dispose();
			System.out.println("Error at sending data: " + ex);
		}
		
		return null;
	}
	
	@Override
	protected String getName()
	{
		return "ConnectionRequestRunner";
	}

}
