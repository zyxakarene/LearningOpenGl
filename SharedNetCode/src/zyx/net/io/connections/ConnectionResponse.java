package zyx.net.io.connections;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import zyx.net.data.ReadableDataObject;

public class ConnectionResponse
{

	public String name;
	public ReadableDataObject object;
	
	public final InetAddress senderHost;
	public final int senderPort;

	public ConnectionResponse(byte[] data) throws IOException
	{
		this.senderHost = null;
		this.senderPort = -1;
		
		loadFrom(data, data.length);
	}
	
	public ConnectionResponse(DatagramPacket packet) throws IOException
	{
		this.senderHost = packet.getAddress();
		this.senderPort = packet.getPort();
		
		byte[] data = packet.getData();
		int dataLength = packet.getLength();
		
		loadFrom(data, dataLength);
	}
	
	public void loadFrom(byte[] data, int dataLength) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, dataLength);
		try (ObjectInputStream stream = new ObjectInputStream(in))
		{
			name = stream.readUTF();
			int length = stream.available();
			byte[] buffer = new byte[length];
			stream.read(buffer, 0, length);
			
			object = new ReadableDataObject(buffer);
		}
	}
}
