package zyx.net.core;

import zyx.net.io.connections.ConnectionRequest;
import zyx.net.io.connections.ConnectionResponse;
import zyx.net.io.responses.ResponseManager;
import zyx.synchronizer.BaseExchange;

public class ConnectionExchange extends BaseExchange<ConnectionRequest, ConnectionResponse>
{
	@Override
	protected void onReplyCompleted(ConnectionResponse reply)
	{
		ResponseManager.getInstance().onResponse(reply);
	}
}
