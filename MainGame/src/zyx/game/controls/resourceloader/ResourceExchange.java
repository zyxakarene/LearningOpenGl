package zyx.game.controls.resourceloader;

import java.util.HashMap;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import java.util.LinkedList;

class ResourceExchange
{

	static final Object LOCK = new Object();

	private static final HashMap<String, ResourceRequest> REQUEST_MAP = new HashMap<>();
	private static final LinkedList<ResourceRequest> LOAD_REQUESTS = new LinkedList<>();
	private static final LinkedList<ResourceRequest> COMPLETED_LOADS = new LinkedList<>();

	static boolean hasLoadRequests()
	{
		synchronized (LOCK)
		{
			return LOAD_REQUESTS.isEmpty() == false;
		}
	}

	static void addLoad(ResourceRequest request)
	{
		synchronized (LOCK)
		{
			if (REQUEST_MAP.containsKey(request.path))
			{
				ResourceRequest otherRequest = REQUEST_MAP.get(request.path);
				otherRequest.mergeFrom(request);

				request.dispose();
			}
			else
			{
				LOAD_REQUESTS.add(request);
				REQUEST_MAP.put(request.path, request);
			}

			LOCK.notify();
		}
	}

	static void addCompleted(ResourceRequest request)
	{
		synchronized (LOCK)
		{
			COMPLETED_LOADS.add(request);
		}
	}

	static ResourceRequest getRequest()
	{
		synchronized (LOCK)
		{
			if (LOAD_REQUESTS.isEmpty())
			{
				return null;
			}
			else
			{
				return LOAD_REQUESTS.removeLast();
			}
		}
	}

	static ResourceRequest getCompleted()
	{
		synchronized (LOCK)
		{
			return COMPLETED_LOADS.removeLast();
		}
	}

	static void sleep()
	{
		synchronized (LOCK)
		{
			try
			{
				LOCK.wait();
			}
			catch (InterruptedException ex)
			{
			}
		}
	}

	static void sendReplies()
	{
		synchronized (LOCK)
		{
			ResourceRequest request;
			while (!COMPLETED_LOADS.isEmpty())
			{
				request = COMPLETED_LOADS.removeFirst();
				if (request.requestCompleted)
				{
					request.complete(request.getData());
				}

				request.dispose();
			}

			REQUEST_MAP.clear();
		}
	}

	static void dispose()
	{
		synchronized (LOCK)
		{
			COMPLETED_LOADS.clear();
			LOAD_REQUESTS.clear();
		}
	}
}
