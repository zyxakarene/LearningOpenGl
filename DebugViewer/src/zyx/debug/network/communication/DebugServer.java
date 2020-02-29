package zyx.debug.network.communication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DebugServer implements Runnable
{
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
				Socket connection = server.accept();
				
				InputStream in = connection.getInputStream();
				OutputStream out = connection.getOutputStream();
				
				ObjectInputStream input = new ObjectInputStream(in);
				ObjectOutputStream output = new ObjectOutputStream(out);
				
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
