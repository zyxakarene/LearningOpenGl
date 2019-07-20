package zyx.net.io.requests;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionData;

public class NetworkRequestDispatcher
{

	private static final ConnectionData NULL_CONNECTION = null;

	private HashMap<String, ArrayList<BaseNetworkRequest>> requestHandlerMap;

	public NetworkRequestDispatcher()
	{
		requestHandlerMap = new HashMap<>();
	}

	public void addRequestHandler(BaseNetworkRequest handler)
	{
		String command = handler.command;

		ArrayList<BaseNetworkRequest> handlers;
		if (requestHandlerMap.containsKey(command))
		{
			handlers = requestHandlerMap.get(command);
		}
		else
		{
			handlers = new ArrayList<>();
			requestHandlerMap.put(command, handlers);
		}

		handlers.add(handler);
	}

	public void requestWithCommand(String command, Object[] params)
	{
		requestWithCommand(command, params, NULL_CONNECTION);
	}

	public void requestWithCommand(String command, Object[] params, ConnectionData[] connections)
	{
		for (ConnectionData connection : connections)
		{
			requestWithCommand(command, params, connection);
		}
	}

	public void requestWithCommand(String command, Object[] params, ConnectionData connection)
	{
		ArrayList<BaseNetworkRequest> handlers = requestHandlerMap.get(command);

		InetAddress host = null;
		int port = -1;
		if (connection != null)
		{
			host = connection.address;
			port = connection.port;
		}

		if (handlers == null)
		{
			System.out.println("[WARNING] Outgoing message, but no handlers: " + command);
		}
		
		if (handlers != null)
		{
			for (BaseNetworkRequest handler : handlers)
			{
				handler.host = host;
				handler.port = port;
				
				handler.sendRequest(params);
			}
		}
	}

	public boolean containsKey(String command)
	{
		return requestHandlerMap.containsKey(command);
	}

	public void dispose()
	{
		requestHandlerMap.clear();
	}
}
