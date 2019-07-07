package zyx.engine.components.network;

import zyx.game.network.PingManager;
import zyx.game.ping.PingController;
import zyx.game.scene.PlayerHandler;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class PingPongNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onPing;
	private PlayerHandler playerHandler;

	public PingPongNetworkCallbacks(PlayerHandler playerHandler)
	{
		this.playerHandler = playerHandler;
		createCallbacks();

		registerCallback(NetworkCommands.PING, onPing);
	}

	private void createCallbacks()
	{
		onPing = (INetworkCallback<Integer>) (Integer id)
		-> {
			PingManager.getInstance().onPing(id);
		};
	}
}
