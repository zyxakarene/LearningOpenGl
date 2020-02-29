package zyx.debug;

import zyx.debug.network.communication.DebugServer;
import zyx.debug.views.DebugView;

public class DebugController
{

	public static final Object SHARED_LOCK = new Object();

	private static DebugView view;
	private static DebugServer server;

	public static void main(String[] args)
	{
		show();
	}

	public static void show()
	{
		if (view == null)
		{
			java.awt.EventQueue.invokeLater(() -> 
			{
				view = new DebugView();
				view.setVisible(true);

				onViewCreated();
			});
		}
	}

	public static void close()
	{
		if (view != null)
		{
			view.dispose();
			view = null;
		}
		
		if (server != null)
		{
			server.stop();
			server = null;
		}
	}

	private static void onViewCreated()
	{
		server = new DebugServer();
		Thread serverThread = new Thread(server, "Server");
		serverThread.start();
	}
}
