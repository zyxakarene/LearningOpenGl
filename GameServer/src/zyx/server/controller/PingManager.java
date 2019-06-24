package zyx.server.controller;

import zyx.game.ping.PingController;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.controllers.NetworkServerChannel;
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
		PlayerManager.getInstance().removePlayer(id);
	}

	@Override
	protected void onSendPingTo(int id)
	{
		Player player = PlayerManager.getInstance().getPlayer(id);
		ServerSender.sendToSingle(NetworkCommands.PING, player.connection);
	}

}
