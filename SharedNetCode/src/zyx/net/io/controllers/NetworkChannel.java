package zyx.net.io.controllers;

public class NetworkChannel
{

	private static BaseNetworkController activeController;
	
	static void setActiveController(BaseNetworkController controller)
	{
		activeController = controller;
	}
	
	public static void sendRequest(String command, Object... params)
	{
		activeController.sendRequestDefault(command, params);
	}
}
