package zyx.server.controller;

import zyx.game.vo.LoginData;
import zyx.server.players.Player;
import zyx.game.vo.PlayerPositionData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.server.players.PlayerManager;

public class ServerNetworkCallbacks extends NetworkCallbacks
{

	private INetworkCallback onPlayerJoin;
	private INetworkCallback onPlayerPos;
	private INetworkCallback onPlayerLogin;

	public ServerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.LOGIN, onPlayerLogin);
		registerCallback(NetworkCommands.PLAYER_JOINED_GAME, onPlayerJoin);
		registerCallback(NetworkCommands.PLAYER_UPDATE_POSITION, onPlayerPos);
	}

	private void onPlayerLogin(LoginData data)
	{
		Player player = PlayerManager.getInstance().createPlayer(data.name, data.connection);
		System.out.println("Player: " + player.id + " joined the game");
		
		ServerSender.sendToSingle(NetworkCommands.AUTHENTICATE, player.connection, player.name, player.id);
		
		onPlayerJoin(player);
	}

	private void onPlayerJoin(Player player)
	{
		//Tell everyone that new guy joined
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_JOINED_GAME, player.connection, player.id);

		//Telling new guy about everyone
		ServerSender.sendToSingle(NetworkCommands.SETUP_GAME, player.connection, player);
	}

	private void onPlayerPos(PlayerPositionData data)
	{
		int id = data.id;
		System.out.println("Server got position from " + id + ": " + data);
		
		Player player = PlayerManager.getInstance().getPlayer(id);
		player.updateFrom(data);

		ServerSender.sendToAllBut(NetworkCommands.PLAYER_UPDATE_POSITION, player.connection, data.position, data.rotation, data.id);
	}

	private void createCallbacks()
	{
		onPlayerJoin = (INetworkCallback<Player>) (Player data)
				-> 
				{
					onPlayerJoin(data);
		};

		onPlayerPos = (INetworkCallback<PlayerPositionData>) (PlayerPositionData data)
				-> 
				{
					onPlayerPos(data);
		};

		onPlayerLogin = (INetworkCallback<LoginData>) (LoginData data)
				-> 
				{
					onPlayerLogin(data);
		};
	}
}
