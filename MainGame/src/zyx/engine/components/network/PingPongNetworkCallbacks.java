package zyx.engine.components.network;

import zyx.game.network.PingManager;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class PingPongNetworkCallbacks extends NetworkCallbacks implements INetworkCallback<Integer>
{

	public PingPongNetworkCallbacks()
	{
		registerCallback(NetworkCommands.PING, this);
	}

	@Override
	public void onNetworkResponse(Integer id)
	{
		PingManager.getInstance().onPing(id);
	}
}
