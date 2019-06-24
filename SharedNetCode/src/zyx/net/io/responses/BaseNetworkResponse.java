package zyx.net.io.responses;

import java.net.InetAddress;
import zyx.net.core.ConnectionHandler;
import zyx.net.data.ReadableDataObject;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.connections.ConnectionData;
import zyx.net.io.connections.ConnectionRequest;

public abstract class BaseNetworkResponse<T>
{

	protected final String command;
	
	private InetAddress senderHost;
	private int senderPort;
	
	protected INetworkCallback<T> callback;

	public BaseNetworkResponse(String command)
	{
		this.command = command;
	}

	public final String getCommand()
	{
		return command;
	}
	
	public final void onMessage(ReadableDataObject data, InetAddress senderHost, int senderPort)
	{
		this.senderHost = senderHost;
		this.senderPort = senderPort;

		T responseData = onMessageRecieved(data);
		
		if (callback != null)
		{
			callback.onNetworkResponse(responseData);
		}
	}

	protected final void sendReply(String command, WriteableDataObject data)
	{
		ConnectionRequest req = new ConnectionRequest(command, data, senderHost, senderPort);
		ConnectionHandler.getInstance().addEntry(req);
	}

	protected abstract T onMessageRecieved(ReadableDataObject data);

	public InetAddress getSenderHost()
	{
		return senderHost;
	}

	public int getSenderPort()
	{
		return senderPort;
	}
	
	public int getSenderUniqueId()
	{
		return InetAddressToId.getIdFromAddress(senderHost, senderPort);
	}

	protected ConnectionData asConnectionData()
	{
		return new ConnectionData(senderHost, senderPort);
	}
	
}
