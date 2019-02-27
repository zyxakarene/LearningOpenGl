package zyx.net.core;

import zyx.net.io.requests.ConnectionRequest;
import zyx.net.io.requests.ConnectionResponse;
import zyx.synchronizer.BaseAsyncSynchronizer;

public class ConnectionHandler extends BaseAsyncSynchronizer<ConnectionRequest, ConnectionResponse>
{

	private static final ConnectionHandler INSTANCE = new ConnectionHandler();

	public static ConnectionHandler getInstance()
	{
		return INSTANCE;
	}

	private ConnectionExchange exchange;
	
	private ConnectionHandler()
	{
		exchange = new ConnectionExchange();
		setExchange(exchange);
	}
	
	@Override
	public void addThreads(int count)
	{
		ConnectionRequestRunner requestRunner = new ConnectionRequestRunner();
		addRunner(requestRunner);
		
		ConnectionResponseRunner responseRunner = new ConnectionResponseRunner();
		addResponseRunner(responseRunner);
	}
}
