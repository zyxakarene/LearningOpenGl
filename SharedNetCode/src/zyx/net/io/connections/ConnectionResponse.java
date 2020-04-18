package zyx.net.io.connections;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
		
		loadFrom(data);
	}
	
	public ConnectionResponse(ConnectionResponseData data) throws IOException
	{
		this.senderHost = data.senderHost;
		this.senderPort = data.senderPort;
		
		loadFrom(data.byteData);
	}
	
	private void loadFrom(byte[] data) throws IOException
	{
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, data.length);
		try (ObjectInputStream stream = new ObjectInputStream(in))
		{
			name = stream.readUTF();
			int length = stream.readShort();
			byte[] buffer = new byte[length];
			stream.readFully(buffer);
			
			object = new ReadableDataObject(buffer);
		}
	}
}
