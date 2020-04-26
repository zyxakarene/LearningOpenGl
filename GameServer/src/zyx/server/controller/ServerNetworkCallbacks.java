package zyx.server.controller;

import zyx.game.login.data.LoginData;
import zyx.server.world.humanoids.players.Player;
import zyx.game.position.data.PositionData;
import zyx.net.io.controllers.NetworkCallbacks;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.INetworkCallback;
import zyx.server.controller.services.PlayerService;
import zyx.server.world.humanoids.players.PlayerManager;

public class ServerNetworkCallbacks extends NetworkCallbacks
{

	public ServerNetworkCallbacks()
	{
		registerCallback(NetworkCommands.LOGIN, (INetworkCallback<LoginData>) this::onPlayerLogin);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, (INetworkCallback<Integer>) this::onPlayerLeave);
		registerCallback(NetworkCommands.CHARACTER_UPDATE_POSITION, (INetworkCallback<PositionData>) this::onPlayerPos);
		registerCallback(NetworkCommands.PING, (INetworkCallback<Integer>) PingManager.getInstance()::onPing);
	}
	
	private void onPlayerLogin(LoginData data)
	{
		Player player = PlayerManager.getInstance().createPlayer(data.name, data.gender, data.connection);
		System.out.println("Player: " + player.id + " - " + player.gender + " joined the game");

		PlayerService.authenticate(player);

		onPlayerJoin(player);
	}

	private void onPlayerJoin(Player player)
	{
		PingManager.getInstance().addEntity(player.id);

		//Tell everyone that new guy joined
		PlayerService.playerJoined(player);

		//Telling new guy about everyone
		PlayerService.setupGame(player);
	}

	private void onPlayerLeave(int playerId)
	{
		PlayerManager.getInstance().removeEntity(playerId);
	}

	private void onPlayerPos(PositionData data)
	{
		int id = data.id;

		Player player = PlayerManager.getInstance().getEntity(id);
		player.updateFrom(data);
	}
}
