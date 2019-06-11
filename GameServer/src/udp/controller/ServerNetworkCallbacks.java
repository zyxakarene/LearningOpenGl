package udp.controller;

import java.util.HashMap;
import java.util.Map;
import udp.Player;
import udp.Server;
import zyx.game.vo.PlayerPositionData;
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

	private void onPlayerJoin(Player player)
	{
		ServerSender.addConnection(player.connection);
		HashMap<Integer, Player> playerMap = Server.playerMap;
		
		System.out.println("Player: " + player.id + " joined the game");
		playerMap.put(player.id, player);
		
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_JOINED_GAME, player.connection, player.id);
		
		for (Map.Entry<Integer, Player> entry : playerMap.entrySet())
		{
			Player value = entry.getValue();
			
			if (value.id != player.id)
			{
				ServerSender.sendToSingle(NetworkCommands.PLAYER_JOINED_GAME, player.connection, value.id);
			}
		}
	}
	
	private void onPlayerPos(PlayerPositionData data)
	{
		int id = data.id;
		System.out.println("Server got position from " + id + ": " + data);
		Player player = Server.playerMap.get(id);
		
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_UPDATE_POSITION, player.connection, data.position, data.rotation, data.id);
	}

	private void createCallbacks()
	{
		onPlayerJoin = (INetworkCallback<Player>) (Player data) -> 
		{
			onPlayerJoin(data);
		};
		
		onPlayerPos = (INetworkCallback<PlayerPositionData>) (PlayerPositionData data) -> 
		{
			onPlayerPos(data);
		};
	}
}
