package zyx.net.io.responses;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.io.requests.ConnectionResponse;

public class ResponseDispatcher
{
	private HashMap<String, ArrayList<IResponse>> responseMap;
	
	public ResponseDispatcher()
	{
		responseMap = new HashMap<>();
	}
	
	public void addResponseCallback(String name, IResponse response)
	{
		ArrayList<IResponse> responses;
		if(responseMap.containsKey(name))
		{
			responses = responseMap.get(name);
		}
		else
		{
			responses = new ArrayList<>();
			responseMap.put(name, responses);
		}
		
		responses.add(response);
	}
	
	public void dispatchWithKey(ConnectionResponse connectionResponse)
	{
		ArrayList<IResponse> responses = responseMap.get(connectionResponse.name);
		
		for (IResponse response : responses)
		{
			response.onMessage(connectionResponse.object, connectionResponse.senderHost, connectionResponse.senderPort);
		}
	}
	
	public boolean containsKey(String name)
	{
		return responseMap.containsKey(name);
	}
	
	public void dispose()
	{
		responseMap.clear();
	}
}
