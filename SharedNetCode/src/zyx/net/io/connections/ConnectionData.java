package zyx.net.io.connections;

import java.net.InetAddress;

public class ConnectionData
{
	public final InetAddress address;
	public final int port;

	public ConnectionData(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}
}
