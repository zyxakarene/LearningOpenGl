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

	private INetworkCallback onPlayerLeave;
	private INetworkCallback onPlayerPos;
	private INetworkCallback onPlayerLogin;
	private INetworkCallback onPlayerPing;

	public ServerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.LOGIN, onPlayerLogin);
		registerCallback(NetworkCommands.PLAYER_LEFT_GAME, onPlayerLeave);
		registerCallback(NetworkCommands.PLAYER_UPDATE_POSITION, onPlayerPos);
		registerCallback(NetworkCommands.PING, onPlayerPing);
	}

	private void onPlayerLogin(LoginData data)
	{
		Player player = PlayerManager.getInstance().createPlayer(data.uniqueId, data.name, data.connection);
		System.out.println("Player: " + player.id + " joined the game");

		ServerSender.sendToSingle(NetworkCommands.AUTHENTICATE, player.connection, player.name, player.id);

		onPlayerJoin(player);
	}

	private void onPlayerJoin(Player player)
	{
		PingManager.getInstance().addEntity(player.id);

		//Tell everyone that new guy joined
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_JOINED_GAME, player.connection, player.id);

		//Telling new guy about everyone
		ServerSender.sendToSingle(NetworkCommands.SETUP_GAME, player.connection, player);
	}

	private void onPlayerLeave(int playerId)
	{
		Player player = PlayerManager.getInstance().getPlayer(playerId);
		PingManager.getInstance().removeEntity(playerId);

		//Tell everyone that the guy left
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_LEFT_GAME, player.connection, playerId);

		PlayerManager.getInstance().removePlayer(player);
	}

	private void onPlayerPos(PlayerPositionData data)
	{
		int id = data.id;

		Player player = PlayerManager.getInstance().getPlayer(id);
		player.updateFrom(data);
	}

	private void createCallbacks()
	{
		onPlayerLeave = (INetworkCallback<Integer>) (Integer data) -> 
		{
			onPlayerLeave(data);
		};

		onPlayerPos = (INetworkCallback<PlayerPositionData>) (PlayerPositionData data) -> 
		{
			onPlayerPos(data);
		};

		onPlayerLogin = (INetworkCallback<LoginData>) (LoginData data) -> 
		{
			onPlayerLogin(data);
		};

		onPlayerPing = (INetworkCallback<Integer>) (Integer data) -> 
		{
			PingManager.getInstance().onPing(data);
		};
	}
}