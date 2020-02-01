package zyx.net.io.controllers;

import zyx.net.io.connections.ConnectionData;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class BaseNetworkController
{	
	NetworkResponseDispatcher responseDispatcher;
	NetworkRequestDispatcher requestDispatcher;
	
	public BaseNetworkController()
	{
		initialize();
	}
	
	protected void addCallbackMap(NetworkCallbacks callbacks)
	{
		responseDispatcher.addCallbackMap(callbacks);
	}
	
	protected void initialize()
	{
		responseDispatcher = new NetworkResponseDispatcher();
		requestDispatcher = new NetworkRequestDispatcher();
		
		NetworkChannel.setActiveController(this);
		NetworkServerChannel.setActiveController(this);
	}
	
	public void dispose()
	{
		if (responseDispatcher != null)
		{
			responseDispatcher.dispose();
			responseDispatcher = null;
		}
		
		if (requestDispatcher != null)
		{
			requestDispatcher.dispose();
			requestDispatcher = null;
		}
		
		NetworkChannel.setActiveController(null);
		NetworkServerChannel.setActiveController(null);
	}
	
	public final void addListeners()
	{
		addResponseHandlersTo(responseDispatcher);
		addRequestsHandlersTo(requestDispatcher);
	}

	protected void addResponseHandlersTo(NetworkResponseDispatcher dispatcher)
	{
	}

	protected void addRequestsHandlersTo(NetworkRequestDispatcher dispatcher)
	{
		
	}

	void sendRequestDefault(String command, Object[] params)
	{
		requestDispatcher.requestWithCommand(command, params);
	}

	void sendRequestSingle(String command, Object[] params, ConnectionData connection)
	{
		requestDispatcher.requestWithCommand(command, params, connection);
	}

	void sendRequestMulti(String command, Object[] params, ConnectionData[] connections)
	{
		requestDispatcher.requestWithCommand(command, params, connections);
	}
}
