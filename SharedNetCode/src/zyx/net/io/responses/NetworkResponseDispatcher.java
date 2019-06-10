package zyx.net.io.responses;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionResponse;
import zyx.net.io.controllers.NetworkCallbacks;

public class NetworkResponseDispatcher
{
	private HashMap<String, ArrayList<BaseNetworkResponse>> responseMap;
	private NetworkCallbacks responseCallbacks;
	
	public NetworkResponseDispatcher()
	{
		responseMap = new HashMap<>();
		
		ResponseManager.getInstance().registerDispatcher(this);
	}
	
	public void setCallbackMap(NetworkCallbacks callbackMap)
	{
		this.responseCallbacks = callbackMap;
	}
	
	public void addResponseCallback(BaseNetworkResponse response)
	{
		String command = response.getCommand();
		
		ArrayList<BaseNetworkResponse> responses;
		if(responseMap.containsKey(command))
		{
			responses = responseMap.get(command);
		}
		else
		{
			responses = new ArrayList<>();
			responseMap.put(command, responses);
		}
		
		response.callback = responseCallbacks.getCallback(command);
		
		responses.add(response);
	}
	
	public void dispatchWithKey(ConnectionResponse connectionResponse)
	{
		ArrayList<BaseNetworkResponse> responses = responseMap.get(connectionResponse.name);
		
		for (BaseNetworkResponse response : responses)
		{
			response.onMessage(connectionResponse.object, connectionResponse.senderHost, connectionResponse.senderPort);
		}
	}
	
	public boolean containsKey(String command)
	{
		return responseMap.containsKey(command);
	}
	
	public void dispose()
	{
		ResponseManager.getInstance().unregisterDispatcher(this);
		
		responseMap.clear();
	}
}
