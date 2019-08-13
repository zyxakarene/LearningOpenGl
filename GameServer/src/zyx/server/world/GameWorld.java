package zyx.server.world;

import zyx.net.io.controllers.BaseNetworkController;
import zyx.server.DebugServerForm;
import zyx.server.controller.ServerNetworkController;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.humanoids.PositionUpdater;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.interactable.chef.Fridge;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.guests.Chair;

public class GameWorld implements IUpdateable
{

	private PositionUpdater posUpdater;
	private PlayerManager playerManager;
	private NpcManager npcManager;

	private RoomItems room;
	private BaseNetworkController networkController;
	
	public DinnerTable dinnerTable;
	public FoodTable foodTable;
	public Fridge chefFridge;
	public Chair guestChair;

	public GameWorld()
	{
		posUpdater = new PositionUpdater();
		playerManager = PlayerManager.getInstance();
		npcManager = NpcManager.getInstance();

		networkController = new ServerNetworkController();
		networkController.addListeners();
		
		room = new RoomItems();
		DebugServerForm.room = room;
		
		npcManager.addChef();
		npcManager.addGuestGroup();
		npcManager.addCleaner();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		npcManager.update(timestamp, elapsedTime);
		playerManager.update(timestamp, elapsedTime);
		
		posUpdater.update(timestamp, elapsedTime);
		
		room.update(timestamp, elapsedTime);
	}
}
