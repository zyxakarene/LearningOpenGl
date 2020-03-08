package zyx.server.controller;

import zyx.server.controller.sending.ServerSender;
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

	private INetworkCallback onPlayerLeave;
	private INetworkCallback onPlayerPos;
	private INetworkCallback onPlayerLogin;
	private INetworkCallback onPlayerPing;

	public ServerNetworkCallbacks()
	{
		createCallbacks();

		registerCallback(NetworkCommands.LOGIN, onPlayerLogin);
		registerCallback(NetworkCommands.CHARACTER_LEFT_GAME, onPlayerLeave);
		registerCallback(NetworkCommands.CHARACTER_UPDATE_POSITION, onPlayerPos);
		registerCallback(NetworkCommands.PING, onPlayerPing);
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
		Player player = PlayerManager.getInstance().getEntity(playerId);
		PingManager.getInstance().removeEntity(playerId);

		//Tell everyone that the guy left
		PlayerService.playerLeft(player);

		PlayerManager.getInstance().removeEntity(player);
	}

	private void onPlayerPos(PositionData data)
	{
		int id = data.id;

		Player player = PlayerManager.getInstance().getEntity(id);
		player.updateFrom(data);
	}

	private void createCallbacks()
	{
		onPlayerLeave = (INetworkCallback<Integer>) this::onPlayerLeave;
		onPlayerPos = (INetworkCallback<PositionData>) this::onPlayerPos;
		onPlayerLogin = (INetworkCallback<LoginData>) this::onPlayerLogin;
		onPlayerPing = (INetworkCallback<Integer>) PingManager.getInstance()::onPing;
	}
}
