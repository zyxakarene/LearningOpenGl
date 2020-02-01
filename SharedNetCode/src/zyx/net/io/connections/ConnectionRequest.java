package zyx.net.io.connections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import zyx.net.core.ConnectionEstablisher;
import zyx.net.data.WriteableDataObject;

public class ConnectionRequest
{

	private final WriteableDataObject data;
	private final String name;
	
	public final InetAddress host;
	public final int port;

	public ConnectionRequest(String name, WriteableDataObject data)
	{
		this(name, data, null, -1);
	}

	public ConnectionRequest(String name, WriteableDataObject data, InetAddress host, int port)
	{
		if (host == null)
		{
			host = ConnectionEstablisher.getInstance().address;
			port = ConnectionEstablisher.getInstance().port;
		}
		
		this.data = data;
		this.name = name;
		this.host = host;
		this.port = port;
	}

	public byte[] getData()
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try (ObjectOutputStream stream = new ObjectOutputStream(out))
		{
			byte[] requestData = data.serialize();
			stream.writeUTF(name);
			stream.writeShort(requestData.length);
			stream.write(requestData);
			stream.flush();
			
			return out.toByteArray();
		}
		catch (IOException ex)
		{
			System.out.println("Could not serialize data: " + ex);
			return new byte[0];
		}
	}

	public void dispose()
	{
		data.dispose();
	}
}
