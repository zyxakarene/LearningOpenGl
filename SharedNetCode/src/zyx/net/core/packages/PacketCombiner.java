package zyx.net.core.packages;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionResponseData;

public class PacketCombiner
{
	private HashMap<String, PacketSender> originMap;

	public PacketCombiner()
	{
		originMap = new HashMap<>();
	}	
	
	public ConnectionResponseData tryCombine(DatagramPacket packet)
	{
		InetAddress address = packet.getAddress();
		String ip = address.getHostAddress();
				
		PacketSender sender = originMap.get(ip);
		if (sender == null)
		{
			sender = new PacketSender(address, packet.getPort());
			originMap.put(ip, sender);
		}
		
		byte[] packetData = packet.getData();
		return sender.addPacketData(packetData, packet.getLength());
	}
}
