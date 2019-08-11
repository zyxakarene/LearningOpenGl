package zyx.server.world;

import zyx.net.io.controllers.BaseNetworkController;
import zyx.server.controller.ServerNetworkController;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.humanoids.PositionUpdater;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.interactable.chef.ChefFridge;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.guests.GuestChair;

public class GameWorld implements IUpdateable
{

	private PositionUpdater posUpdater;
	private PlayerManager playerManager;
	private NpcManager npcManager;

	private BaseNetworkController networkController;
	
	public DinnerTable dinnerTable;
	public FoodTable foodTable;
	public ChefFridge chefFridge;
	public GuestChair guestChair;

	public GameWorld()
	{
		posUpdater = new PositionUpdater();
		playerManager = PlayerManager.getInstance();
		npcManager = NpcManager.getInstance();

		networkController = new ServerNetworkController();
		networkController.addListeners();
		
		npcManager.addChef();
		npcManager.addGuestGroup();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		npcManager.update(timestamp, elapsedTime);
		
		posUpdater.update(timestamp, elapsedTime);
	}
}
