package zyx.debug.views.network;

public enum DebugNetworkType
{
	REQUEST("Requests"),
	RESPONSE("Responses");
	
	public final String name;

	private DebugNetworkType(String name)
	{
		this.name = name;
	}
}
