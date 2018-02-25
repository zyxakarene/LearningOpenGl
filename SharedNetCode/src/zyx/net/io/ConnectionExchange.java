package zyx.net.io;

import java.util.LinkedList;
import zyx.net.io.requests.ConnectionRequest;
import zyx.net.io.requests.ConnectionResponse;
import zyx.net.io.responses.ResponseManager;

class ConnectionExchange
{
	static final int HANDSHAKE_LENGTH = 8;
	
	static final Object REQUEST_LOCK = new Object();
	static final Object RESPONSE_LOCK = new Object();

	private static final LinkedList<ConnectionRequest> REQUESTS = new LinkedList<>();
	private static final LinkedList<ConnectionResponse> RESPONSES = new LinkedList<>();

	static boolean hasRequests()
	{
		synchronized (REQUEST_LOCK)
		{
			return REQUESTS.isEmpty() == false;
		}
	}

	static boolean hasResponses()
	{
		synchronized (RESPONSE_LOCK)
		{
			return RESPONSES.isEmpty() == false;
		}
	}
	
	static void addRequest(ConnectionRequest packet)
	{
		synchronized(REQUEST_LOCK)
		{
			REQUESTS.add(packet);
			REQUEST_LOCK.notify();
		}
	}
	
	static void addResponse(ConnectionResponse request)
	{
		synchronized (RESPONSE_LOCK)
		{
			RESPONSES.add(request);
			RESPONSE_LOCK.notify();
		}
	}
	
	static ConnectionRequest getRequest()
	{
		synchronized (REQUEST_LOCK)
		{
			if (REQUESTS.isEmpty())
			{
				return null;
			}
			else
			{
				return REQUESTS.removeLast();
			}
		}
	}

	static ConnectionResponse getResponse()
	{
		synchronized (RESPONSE_LOCK)
		{
			return RESPONSES.removeLast();
		}
	}

	static void sleepRequest()
	{
		synchronized (REQUEST_LOCK)
		{
			try
			{
				REQUEST_LOCK.wait();
			}
			catch (InterruptedException ex)
			{
			}
		}
	}

	static void dispose()
	{
		synchronized (REQUEST_LOCK)
		{
			REQUESTS.clear();
		}
		
		synchronized (RESPONSE_LOCK)
		{
			RESPONSES.clear();
		}
	}

	static void sendResponses()
	{
		synchronized (RESPONSE_LOCK)
		{
			while (RESPONSES.isEmpty() == false)
			{				
				ConnectionResponse response = RESPONSES.removeLast();
				
				ResponseManager.getInstance().onResponse(response);
			}
		}
	}
}
