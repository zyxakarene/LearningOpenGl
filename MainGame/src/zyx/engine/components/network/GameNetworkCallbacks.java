package zyx.engine.components.network;

import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.utils.cheats.Print;

public class GameNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onAuth;

	public GameNetworkCallbacks()
	{
		createCallbacks();
		
		registerCallback(NetworkCommands.AUTH, onAuth);
	}

	private void onAuth(int id)
	{
		Print.out("User authenticated as ID:", id);
	}
	
	private void createCallbacks()
	{
		onAuth = (INetworkCallback<Integer>) (Integer playerId) ->
		{
			onAuth(playerId);
			NetworkChannel.sendRequest(NetworkCommands.JOIN_GAME, playerId);
		};
	}
}
