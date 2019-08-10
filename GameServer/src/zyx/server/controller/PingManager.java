package zyx.server.controller;

import zyx.game.ping.PingController;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;

public class PingManager extends PingController
{

	private static final PingManager INSTANCE = new PingManager();

	public static PingManager getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	protected void onPingTimeout(int id)
	{
		System.out.println(id + " was kicked from the server");
		Player player = PlayerManager.getInstance().getEntity(id);
		ServerSender.sendToAllBut(NetworkCommands.PLAYER_LEFT_GAME, player.connection, id);
		
		PlayerManager.getInstance().removeEntity(player);
	}

	@Override
	protected void onSendPingTo(int id)
	{
		Player player = PlayerManager.getInstance().getEntity(id);
		ServerSender.sendToSingle(NetworkCommands.PING, player.connection, player.id);
	}

}
