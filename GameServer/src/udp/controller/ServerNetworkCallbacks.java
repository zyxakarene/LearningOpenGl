package udp.controller;

import udp.Player;
import udp.Server;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class ServerNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onPlayerJoin;

	public ServerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.JOIN_GAME, onPlayerJoin);
	}

	private void onJoin(Player player)
	{
		System.out.println("Player: " + player.id + " joined the game");
		Server.playerMap.put(player.id, player);
		
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_JOINED, player.connection, player.id);
	}

	private void createCallbacks()
	{
		onPlayerJoin = (INetworkCallback<Player>) (Player data) -> 
		{
			onJoin(data);
		};
	}
}
