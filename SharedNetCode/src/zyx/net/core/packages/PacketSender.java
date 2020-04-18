package zyx.net.core.packages;

import java.net.InetAddress;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionResponseData;

class PacketSender
{

	private static final int HEADER_LENGTH = PacketSplitter.HEADER_LENGTH;
	
	private InetAddress address;
	private int port;

	private HashMap<Short, PacketGroup> groupMap;

	PacketSender(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
		this.groupMap = new HashMap<>();
	}

	ConnectionResponseData addPacketData(byte[] packetData, int packetLength)
	{
		byte id1 = packetData[0];
		byte id2 = packetData[1];

		byte index1 = packetData[2];
		byte index2 = packetData[3];

		byte length1 = packetData[4];
		byte length2 = packetData[5];

		short id = (short) ((id1 << 8) + id2);
		short index = (short) ((index1 << 8) + index2);
		short length = (short) ((length1 << 8) + length2);

		PacketGroup group = groupMap.get(id);
		if (group == null)
		{
			group = new PacketGroup();
			groupMap.put(id, group);
		}

		byte[] data = new byte[packetLength - HEADER_LENGTH];
		System.arraycopy(packetData, HEADER_LENGTH, data, 0, data.length);
		
		group.addData(index, length, data);

		if (group.isComplete())
		{
			byte[] combinedData = group.getCombinedData();

			group.dispose();
			groupMap.remove(id);

			return new ConnectionResponseData(combinedData, address, port);
		}

		return null;
	}
}
