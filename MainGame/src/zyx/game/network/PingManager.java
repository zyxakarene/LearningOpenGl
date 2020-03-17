package zyx.game.network;

import zyx.engine.scene.SceneManager;
import zyx.game.models.GameModels;
import zyx.game.ping.PingController;
import zyx.game.scene.SceneType;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;
import zyx.utils.cheats.Print;

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
		Print.out("The server stopped responding");
		SceneManager.getInstance().changeScene(SceneType.MENU);
	}

	@Override
	protected void onSendPingTo(int id)
	{
		NetworkChannel.sendRequest(NetworkCommands.PING, GameModels.player.playerId);
	}
}
