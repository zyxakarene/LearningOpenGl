package zyx.game.network;

import zyx.game.ping.PingController;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.utils.cheats.Print;

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
		Print.out("The server stopped responding");
	}

	@Override
	protected void onSendPingTo(int id)
	{
		NetworkChannel.sendRequest(NetworkCommands.PING);
	}
}
