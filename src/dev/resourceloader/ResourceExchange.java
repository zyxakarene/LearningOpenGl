package dev.resourceloader;

import dev.resourceloader.requests.ResourceRequest;
import java.util.LinkedList;

public class ResourceExchange
{

	static final Object LOCK = new Object();

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
			LOAD_REQUESTS.add(request);
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
			return LOAD_REQUESTS.removeLast();
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
				
				request.callback.resourceLoaded(request.getData());
			}
		}
	}
}
