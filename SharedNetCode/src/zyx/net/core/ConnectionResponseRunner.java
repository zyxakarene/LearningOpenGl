package zyx.net.core;

import java.io.IOException;
import java.net.DatagramPacket;
import zyx.net.core.packages.PacketCombiner;
import zyx.net.io.connections.ConnectionRequest;
import zyx.net.io.connections.ConnectionResponse;
import zyx.net.io.connections.ConnectionResponseData;
import zyx.synchronizer.ResponseRunner;

public class ConnectionResponseRunner extends ResponseRunner<ConnectionRequest, ConnectionResponse>
{
	private PacketCombiner combiner;

	public ConnectionResponseRunner()
	{
		combiner = new PacketCombiner();
	}
	
	@Override
	protected ConnectionResponse getReply()
	{
		try
		{
			DatagramPacket packet = PersistentConnection.getInstance().receive();
			ConnectionResponseData responseData = combiner.tryCombine(packet);
			
			if (responseData != null)
			{
				ConnectionResponse response = new ConnectionResponse(responseData);

				DebugNetworkList.addResponse(response.object, response.name);

				return response;
			}
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
