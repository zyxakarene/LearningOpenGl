package zyx.net.core.packages;

import java.net.DatagramPacket;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionResponseData;

public class PacketCombiner
{
	private HashMap<Integer, PacketSender> originMap;

	public PacketCombiner()
	{
		originMap = new HashMap<>();
	}	
	
	public ConnectionResponseData tryCombine(DatagramPacket packet)
	{
		int port = packet.getPort();
				
		PacketSender sender = originMap.get(port);
		if (sender == null)
		{
			sender = new PacketSender(packet.getAddress(), port);
			originMap.put(port, sender);
		}
		
		byte[] packetData = packet.getData();
		return sender.addPacketData(packetData, packet.getLength());
	}
}
