package zyx.net.io.requests;

import java.net.InetAddress;
import zyx.net.core.ConnectionHandler;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.connections.ConnectionRequest;

public abstract class BaseNetworkRequest
{

	protected final String command;

	InetAddress host;
	int port;

	public BaseNetworkRequest(String command)
	{
		this.command = command;
		
		host = null;
		port = -1;
	}

	final void sendRequest(Object[] params)
	{
		WriteableDataObject data = new WriteableDataObject();
		getDataObject(data, params);
		
		ConnectionRequest req = new ConnectionRequest(command, data, host, port);
		ConnectionHandler.getInstance().addEntry(req);
	}

	public final String getCommand()
	{
		return command;
	}

	protected abstract void getDataObject(WriteableDataObject data, Object[] params);

}
