package zyx.net.io.responses;

import java.util.ArrayList;
import java.util.LinkedList;
import zyx.net.io.connections.ConnectionResponse;

public class ResponseManager
{

	private static final ResponseManager INSTANCE = new ResponseManager();
	
	private final ArrayList<NetworkResponseDispatcher> dispatchers;
	
	private static final LinkedList<NetworkResponseDispatcher> LIST_HELPER = new LinkedList<>();
	
	
	private ResponseManager()
	{
		dispatchers = new ArrayList<>();
	}
	
	void registerDispatcher(NetworkResponseDispatcher dispatcher)
	{
		synchronized(dispatchers)
		{
			dispatchers.add(dispatcher);
		}
	}
	
	void unregisterDispatcher(NetworkResponseDispatcher dispatcher)
	{
		synchronized(dispatchers)
		{
			dispatchers.remove(dispatcher);
		}
	}

	public static ResponseManager getInstance()
	{
		return INSTANCE;
	}

	public void onResponse(ConnectionResponse response)
	{
		synchronized(dispatchers)
		{
			for (NetworkResponseDispatcher dispatcher : dispatchers)
			{
				if (dispatcher.containsKey(response.name))
				{
					LIST_HELPER.addLast(dispatcher);
				}
			}
		}
		
		while (!LIST_HELPER.isEmpty())
		{			
			NetworkResponseDispatcher dispatcher = LIST_HELPER.removeFirst();
			dispatcher.dispatchWithKey(response);
		}
	}
}
