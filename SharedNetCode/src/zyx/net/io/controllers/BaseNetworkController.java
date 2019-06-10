package zyx.net.io.controllers;

import zyx.net.io.connections.ConnectionData;
import zyx.net.io.requests.NetworkRequestDispatcher;
import zyx.net.io.responses.NetworkResponseDispatcher;

public class BaseNetworkController
{	
	NetworkResponseDispatcher responseDispatcher;
	NetworkRequestDispatcher requestDispatcher;
	NetworkCallbacks responseCallbacks;
	
	public BaseNetworkController()
	{
		this(new NetworkCallbacks());
	}
	
	public BaseNetworkController(NetworkCallbacks callbacks)
	{
		initialize();
		responseCallbacks = callbacks;
	}
	
	protected void initialize()
	{
		responseDispatcher = new NetworkResponseDispatcher();
		requestDispatcher = new NetworkRequestDispatcher();
		
		NetworkChannel.setActiveController(this);
		NetworkServerChannel.setActiveController(this);
	}
	
	public final void addListeners()
	{
		responseDispatcher.setCallbackMap(responseCallbacks);
		
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
