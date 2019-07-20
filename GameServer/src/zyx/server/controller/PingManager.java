package zyx.server.controller;

import zyx.game.ping.PingController;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.players.Player;
import zyx.server.players.PlayerManager;

public class PingManager extends PingController
{

	private static final PingManager instance = new PingManager();

	public static PingManager getInstance()
	{
		return instance;
	}
	
	@Override
	protected void onPingTimeout(int id)
	{
		System.out.println(id + " was kicked from the server");
		Player player = PlayerManager.getInstance().getPlayer(id);
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_LEFT_GAME, player.connection, id);
		
		PlayerManager.getInstance().removePlayer(player);
	}

	@Override
	protected void onSendPingTo(int id)
	{
		Player player = PlayerManager.getInstance().getPlayer(id);
		ServerSender.sendToSingle(NetworkCommands.PING, player.connection);
	}

}
