package zyx.debug.network.communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DebugServer implements Runnable
{

	static final Object SERVER_LOCK = new Object();

	private ServerSocket server;
	private boolean active;

	private DebugCommunicator communicator;

	public DebugServer()
	{
		try
		{
			active = true;
			server = new ServerSocket(4444);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void run()
	{
		while (active)
		{
			try
			{
				synchronized (SERVER_LOCK)
				{
					while (communicator != null && communicator.isActive())
					{
						System.out.println("Waiting for current connection to die, before listening again...");
						
						try
						{
							SERVER_LOCK.wait();
						}
						catch (InterruptedException ex)
						{
						}
						
						communicator.dispose();
						communicator = null;
					}
				}

				System.out.println("Listening for incoming connections...");
				
				Socket connection = server.accept();

				System.out.println("Got incoming connection from game: " + connection);

				InputStream in = connection.getInputStream();
				OutputStream out = connection.getOutputStream();

				DataInputStream input = new DataInputStream(in);
				DataOutputStream output = new DataOutputStream(out);

				communicator = new DebugCommunicator(input, output);
				communicator.startThreads();
			}
			catch (IOException ex)
			{
				System.out.println("Connection killed or something! Let's listen for a new one!");
			}
		}
	}

	public void stop()
	{
		active = false;
	}
}
