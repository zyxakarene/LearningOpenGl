package zyx.net.io.responses;

import java.util.ArrayList;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.requests.ConnectionResponse;

public class ResponseManager
{

	private static final ResponseManager INSTANCE = new ResponseManager();
	
	private final ArrayList<ResponseDispatcher> dispatchers;
	
	private ResponseManager()
	{
		dispatchers = new ArrayList<>();
	}
	
	public void registerDispatcher(ResponseDispatcher dispatcher)
	{
		synchronized(dispatchers)
		{
			dispatchers.add(dispatcher);
		}
	}
	
	public void unregisterDispatcher(ResponseDispatcher dispatcher)
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
			for (ResponseDispatcher dispatcher : dispatchers)
			{
				if (dispatcher.containsKey(response.name))
				{
					dispatcher.dispatchWithKey(response);
				}
			}
		}
	}
}
