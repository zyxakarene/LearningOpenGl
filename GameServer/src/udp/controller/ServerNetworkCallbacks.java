package udp.controller;

import com.sun.javafx.geom.Vec4f;
import udp.Player;
import udp.Server;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;

public class ServerNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onPlayerJoin;
	private INetworkCallback onPlayerPos;

	public ServerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.PLAYER_JOINED_GAME, onPlayerJoin);
		registerCallback(NetworkCommands.PLAYER_UPDATE_POSITION, onPlayerPos);
	}

	private void onJoin(Player player)
	{
		ServerSender.addConnection(player.connection);
		
		System.out.println("Player: " + player.id + " joined the game");
		Server.playerMap.put(player.id, player);
		
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_JOINED_GAME, player.connection, player.id);
	}
	
	private void onPos(Vec4f pos)
	{
		int id = (int)pos.w;
		System.out.println("Server got position from " + id + ": " + pos);
		Player player = Server.playerMap.get(id);
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_UPDATE_POSITION, player.connection, pos);
	}

	private void createCallbacks()
	{
		onPlayerJoin = (INetworkCallback<Player>) (Player data) -> 
		{
			onJoin(data);
		};
		
		onPlayerPos = (INetworkCallback<Vec4f>) (Vec4f pos) -> 
		{
			onPos(pos);
		};
	}
}
