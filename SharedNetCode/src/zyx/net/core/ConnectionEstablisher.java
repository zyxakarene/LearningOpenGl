package zyx.net.core;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectionEstablisher
{

	private static final ConnectionEstablisher INSTANCE = new ConnectionEstablisher();

	public static ConnectionEstablisher getInstance()
	{
		return INSTANCE;
	}

	public InetAddress address;
	public int port;

	private ConnectionEstablisher()
	{
	}

	public void connect(String host, int port)
	{
		try
		{
			InetAddress inetAdress = InetAddress.getByName(host);
			PersistentConnection.getInstance().connectTo(inetAdress, port);

			this.address = inetAdress;
			this.port = port;
		}
		catch (UnknownHostException | SocketException ex)
		{
			String errorMsg = String.format("Could not connect socket to %s:%s", host, port);
			throw new RuntimeException(errorMsg, ex);
		}
	}

	public void listen(int port)
	{
		try
		{
			PersistentConnection.getInstance().listenTo(port);

			this.address = null;
			this.port = port;
		}
		catch (SocketException ex)
		{
			String errorMsg = String.format("Could not listen to port %s", port);
			throw new RuntimeException(errorMsg, ex);
		}
	}
}
