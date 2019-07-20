package zyx.net.core;

import java.io.IOException;
import java.net.DatagramPacket;
import zyx.net.io.connections.ConnectionRequest;
import zyx.net.io.connections.ConnectionResponse;
import zyx.synchronizer.ResponseRunner;

public class ConnectionResponseRunner extends ResponseRunner<ConnectionRequest, ConnectionResponse>
{

	@Override
	protected ConnectionResponse getReply()
	{
		try
		{
			DatagramPacket packet = PersistentConnection.getInstance().receive();

			ConnectionResponse response = new ConnectionResponse(packet);

			DebugNetworkList.addResponse(response.object, response.name);
			
			return response;
		}
		catch (IOException ex)
		{
			dispose();
			System.out.println("Error at receiving data: " + ex);
		}

		return null;
	}

	@Override
	protected String getName()
	{
		return "ConnectionResponseRunner";
	}
}
