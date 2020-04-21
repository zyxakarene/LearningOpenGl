package zyx.server.controller;

import zyx.game.ping.PingController;
import zyx.server.controller.services.PingService;
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
		PlayerManager.getInstance().removeEntity(id);
	}

	@Override
	protected void onSendPingTo(int id)
	{
		Player player = PlayerManager.getInstance().getEntity(id);
		PingService.sendPingTo(player);
	}

}
