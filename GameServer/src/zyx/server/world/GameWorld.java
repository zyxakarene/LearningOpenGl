package zyx.server.world;

import zyx.net.io.controllers.BaseNetworkController;
import zyx.server.controller.ServerNetworkController;
import zyx.server.players.PlayerManager;
import zyx.server.players.PositionUpdater;
import zyx.server.utils.IUpdateable;

public class GameWorld implements IUpdateable
{

	private PositionUpdater posUpdater;
	private PlayerManager playerManager;

	private BaseNetworkController networkController;

	public GameWorld()
	{
		posUpdater = new PositionUpdater();
		playerManager = PlayerManager.getInstance();

		networkController = new ServerNetworkController();
		networkController.addListeners();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		posUpdater.update(timestamp, elapsedTime);
	}
}
