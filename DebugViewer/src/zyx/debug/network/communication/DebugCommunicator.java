package zyx.debug.network.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;

class DebugCommunicator
{
	private Thread receiverThread;
	private Thread senderThread;
	
	private DebugReceiver receiver;
	private DebugSender sender;

	DebugCommunicator(DataInputStream in, DataOutputStream out)
	{
		receiver = new DebugReceiver(in);
		sender = new DebugSender(out);
	}
	
	void startThreads()
	{
		receiverThread = new Thread(receiver, "Receiver");
		senderThread = new Thread(sender, "Sender");
		
		receiverThread.setDaemon(true);
		senderThread.setDaemon(true);
		
		receiverThread.start();
		senderThread.start();
	}
	
	boolean isActive()
	{
		if (receiver != null && sender != null)
		{
			return receiver.isActive() && sender.isActive();
		}
		
		return false;
	}

	void dispose()
	{
		System.out.println("Connection dropped, killing " + this);
		
		if (receiver != null)
		{
			receiver.fail();
			receiver = null;
		}
		
		if (sender != null)
		{
			sender.fail();
			sender = null;
		}
		
		if (receiverThread != null)
		{
			receiverThread.interrupt();
			receiverThread = null;
		}
		
		if (senderThread != null)
		{
			senderThread.interrupt();
			senderThread = null;
		}
	}
}
