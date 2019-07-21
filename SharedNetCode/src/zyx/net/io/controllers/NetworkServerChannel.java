package zyx.net.io.controllers;

import zyx.net.io.connections.ConnectionData;

public class NetworkServerChannel
{

	private static BaseNetworkController activeController;
	
	static void setActiveController(BaseNetworkController controller)
	{
		activeController = controller;
	}
	
	public static void sendRequestSingle(String command, ConnectionData connection, Object[] params)
	{
		activeController.sendRequestSingle(command, params, connection);
	}
	
	public static void sendRequestMulti(String command, ConnectionData[] connections, Object[] params)
	{
		activeController.sendRequestMulti(command, params, connections);
	}
}
