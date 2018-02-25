package zyx.net.io;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import zyx.net.io.requests.ConnectionRequest;

public class ConnectionLoader implements IDisposeable
{

	private static final ConnectionLoader INSTANCE = new ConnectionLoader();

	public static ConnectionLoader getInstance()
	{
		return INSTANCE;
	}

	private ConnectionRequestRunner requestRunner;
	private ConnectionResponseRunner responseRunner;
	
	public InetAddress address;
	public int port;
	
	private ConnectionLoader()
	{
	}

	public void startThreads()
	{
		requestRunner = new ConnectionRequestRunner();
		responseRunner = new ConnectionResponseRunner();
		
		Thread requestThread = new Thread(requestRunner);
		Thread responseThread = new Thread(responseRunner);
		
		requestThread.setName("ConnectionRequestRunner");
		responseThread.setName("ConnectionResponseRunner");
		
		requestThread.setDaemon(true);
		responseThread.setDaemon(true);
		
		requestThread.start();
		responseThread.start();
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

	public void handleConnectionResponses()
	{
		ConnectionExchange.sendResponses();
	}

	public void addRequest(ConnectionRequest request)
	{
		ConnectionExchange.addRequest(request);
	}

	@Override
	public void dispose()
	{
		requestRunner.dispose();
		responseRunner.dispose();
		
		ConnectionExchange.dispose();
	}
}
