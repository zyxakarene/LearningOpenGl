package zyx.net.io.controllers;

import java.util.HashMap;
import zyx.net.io.responses.INetworkCallback;

public abstract class NetworkCallbacks
{
	private HashMap<String, INetworkCallback> callbacks;

	public NetworkCallbacks()
	{
		callbacks = new HashMap<>();
	}
	
	protected void registerCallback(String command, INetworkCallback callback)
	{
		callbacks.put(command, callback);
	}
	
	protected void dispose()
	{
		callbacks.clear();
		callbacks = null;
	}
	
	public INetworkCallback getCallback(String command)
	{
		return callbacks.get(command);
	}

}
