package zyx.server;

import zyx.server.controller.ServerNetworkController;
import zyx.net.core.ConnectionEstablisher;
import zyx.net.core.ConnectionHandler;
import zyx.net.io.controllers.BaseNetworkController;

public class Server
{
	private BaseNetworkController networkController;
	
	public void start()
	{
		ConnectionEstablisher.getInstance().listen(8888);
		ConnectionHandler.getInstance().addThreads(1);

		networkController = new ServerNetworkController();
		networkController.addListeners();

		System.out.println("Server setup");
		System.out.println("Start listening..");
		while (true)
		{
			ConnectionHandler.getInstance().handleReplies();
			
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException ex)
			{
			}
		}
	}
}
