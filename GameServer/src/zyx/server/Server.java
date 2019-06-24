package zyx.server;

import zyx.server.controller.ServerNetworkController;
import zyx.net.core.ConnectionEstablisher;
import zyx.net.core.ConnectionHandler;
import zyx.net.io.controllers.BaseNetworkController;
import zyx.server.controller.PingManager;
import zyx.server.utils.DeltaTime;

public class Server
{

	private BaseNetworkController networkController;

	public void start()
	{
		ConnectionEstablisher.getInstance().listen(8888);
		ConnectionHandler.getInstance().addThreads(1);

		networkController = new ServerNetworkController();
		networkController.addListeners();

		DeltaTime.start();
		System.out.println("Server setup");
		System.out.println("Start listening..");
		while (true)
		{
			DeltaTime.update();
			long timeStamp = DeltaTime.getTimestamp();
			int elapsedTime = DeltaTime.getElapsedTime();

			ConnectionHandler.getInstance().handleReplies();
			PingManager.getInstance().update(timeStamp, elapsedTime);

			try
			{
				Thread.sleep(16);
			}
			catch (InterruptedException ex)
			{
			}
		}
	}
}
