package zyx.net.core;

import zyx.net.core.packages.PacketSplitter;
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

		DebugNetworkList.addRequest(byteData);
		
		DatagramPacket[] packets = PacketSplitter.splitDataToPackets(byteData, entry.host, entry.port);
		try
		{
			for (DatagramPacket packet : packets)
			{
				PersistentConnection.getInstance().send(packet);
			}
		}
		catch (IOException ex)
		{
			dispose();
			System.err.println("Error at sending data: " + ex);
		}
		
		entry.dispose();
		
		return null;
	}
	
	@Override
	protected String getName()
	{
		return "ConnectionRequestRunner";
	}

}
