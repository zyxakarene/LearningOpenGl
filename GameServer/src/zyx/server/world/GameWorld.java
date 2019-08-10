package zyx.server.world;

import zyx.net.io.controllers.BaseNetworkController;
import zyx.server.controller.ServerNetworkController;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.humanoids.PositionUpdater;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.NpcManager;

public class GameWorld implements IUpdateable
{

	private PositionUpdater posUpdater;
	private PlayerManager playerManager;
	private NpcManager npcManager;

	private BaseNetworkController networkController;

	public GameWorld()
	{
		posUpdater = new PositionUpdater();
		playerManager = PlayerManager.getInstance();
		npcManager = NpcManager.getInstance();

		networkController = new ServerNetworkController();
		networkController.addListeners();
		
		npcManager.addChef();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		npcManager.update(timestamp, elapsedTime);
		
		posUpdater.update(timestamp, elapsedTime);
	}
}
