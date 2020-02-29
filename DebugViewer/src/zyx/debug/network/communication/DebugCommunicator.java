package zyx.debug.network.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DebugCommunicator
{
	private DebugReceiver receiver;
	private DebugSender sender;

	public DebugCommunicator(ObjectInputStream in, ObjectOutputStream out)
	{
		receiver = new DebugReceiver(in);
		sender = new DebugSender(out);
	}
	
	public void startThreads()
	{
		Thread senderThread = new Thread(sender, "Sender");
		Thread receiverThread = new Thread(receiver, "Receiver");
		
		senderThread.setDaemon(true);
		receiverThread.setDaemon(true);
		
		senderThread.start();
		receiverThread.start();
	}
}
