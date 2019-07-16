package zyx.net.core;

import java.io.IOException;
import java.util.ArrayList;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.connections.ConnectionResponse;

public class DebugNetworkList
{

	private static final ArrayList<String> RESPONSES_NAMES = new ArrayList<>();
	private static final ArrayList<String> REQUESTS_NAMES = new ArrayList<>();
	
	private static final ArrayList<ReadableDataObject> RESPONSES = new ArrayList<>();
	private static final ArrayList<ReadableDataObject> REQUESTS = new ArrayList<>();

	static void addRequest(byte[] byteData)
	{
		ConnectionResponse response = null;
		try
		{
			response = new ConnectionResponse(byteData);
		}
		catch (IOException ex)
		{
		}

		synchronized (REQUESTS)
		{
			if (response != null)
			{
				REQUESTS.add(response.object);
				REQUESTS_NAMES.add(response.name);
			}
		}
	}

	static void addResponse(ReadableDataObject data, String command)
	{
		synchronized (RESPONSES)
		{
			RESPONSES.add(data);
			RESPONSES_NAMES.add(command);
		}
	}

	public static boolean getNewestRequests(ArrayList<ReadableDataObject> out, ArrayList<String> names)
	{
		boolean hasEntries;
		
		synchronized (REQUESTS)
		{
			hasEntries = REQUESTS.size() > 0;
			out.addAll(REQUESTS);
			names.addAll(REQUESTS_NAMES);
			
			REQUESTS.clear();
			REQUESTS_NAMES.clear();
		}
		
		return hasEntries;
	}

	public static boolean getNewestResponses(ArrayList<ReadableDataObject> out, ArrayList<String> names)
	{
		boolean hasEntries;
		
		synchronized (RESPONSES)
		{
			hasEntries = RESPONSES.size() > 0;
			out.addAll(RESPONSES);
			names.addAll(RESPONSES_NAMES);
			
			RESPONSES.clear();
			RESPONSES_NAMES.clear();
		}
		
		return hasEntries;
	}

}
