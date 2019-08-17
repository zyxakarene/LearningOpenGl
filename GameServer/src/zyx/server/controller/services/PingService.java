package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.players.Player;

public class PingService
{
	public static void sendPingTo(Player player)
	{
		ServerSender.sendWithType(SendType.toSingle(player.connection), NetworkCommands.PING, player.id);
	}
	
	
}
