package zyx.server;

import zyx.net.core.ConnectionEstablisher;
import zyx.net.core.ConnectionHandler;
import zyx.server.controller.PingManager;
import zyx.server.utils.DeltaTime;
import zyx.server.world.GameWorld;

public class Server
{

	private GameWorld world;
	
	public void start()
	{
		ConnectionEstablisher.getInstance().listen(8888);
		ConnectionHandler.getInstance().addThreads(1);

		DeltaTime.start();
		System.out.println("Server setup");
		System.out.println("Start listening..");
		
		world = new GameWorld();
		
		while (true)
		{
			DeltaTime.update();
			long timeStamp = DeltaTime.getTimestamp();
			int elapsedTime = DeltaTime.getElapsedTime();

			ConnectionHandler.getInstance().handleReplies();
			PingManager.getInstance().update(timeStamp, elapsedTime);

			world.update(timeStamp, elapsedTime);
			
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
