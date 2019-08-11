package zyx.server.world;

import java.awt.Graphics;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.interactable.chef.ChefFridge;
import zyx.server.world.interactable.chef.ChefStove;
import zyx.server.world.interactable.chef.OrderMonitor;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.guests.GuestChair;
import zyx.server.world.interactable.guests.GuestExitPoint;
import zyx.server.world.interactable.player.OrderMachine;

public class RoomItems implements IUpdateable
{

	public static RoomItems instance;

	public ChefFridge[] fridges;
	public ChefStove[] stoves;
	public FoodTable[] foodTables;

	public DinnerTable[] dinnerTables;
	public GuestChair[] chairs;

	public OrderMachine orderMachine;
	public OrderMonitor orderMonitor;

	public GuestExitPoint exitPoint;

	public RoomItems()
	{
		instance = this;

		fridges = new ChefFridge[1];
		fridges[0] = new ChefFridge();
		fridges[0].updatePosition(200, 15, 0);

		stoves = new ChefStove[1];
		stoves[0] = new ChefStove();
		stoves[0].updatePosition(250, 25, 0);

		foodTables = new FoodTable[1];
		foodTables[0] = new FoodTable();
		foodTables[0].updatePosition(200, 100, 0);

		chairs = new GuestChair[1];
		chairs[0] = new GuestChair();
		chairs[0].updatePosition(25, 150, 0);

		dinnerTables = new DinnerTable[1];
		dinnerTables[0] = new DinnerTable(chairs);
		dinnerTables[0].updatePosition(25, 100, 0);

		chairs[0].linkToTable(dinnerTables[0]);

		orderMachine = new OrderMachine();
		orderMachine.updatePosition(200, 200, 0);
		orderMonitor = new OrderMonitor();
		orderMonitor.updatePosition(150, 25, 0);

		exitPoint = new GuestExitPoint();
		exitPoint.updatePosition(0, 0, 0);
	}

	public ChefFridge getNearestFridge(Vector3f from)
	{
		return fridges[0];
	}

	public OrderMonitor orderMonitor()
	{
		return orderMonitor;
	}

	public OrderMachine orderMachine()
	{
		return orderMachine;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		fridges[0].update(timestamp, elapsedTime);
		stoves[0].update(timestamp, elapsedTime);
		foodTables[0].update(timestamp, elapsedTime);

		dinnerTables[0].update(timestamp, elapsedTime);

		orderMonitor.update(timestamp, elapsedTime);
	}

	public void draw(Graphics g)
	{
		fridges[0].draw(g);
		stoves[0].draw(g);
		foodTables[0].draw(g);

		dinnerTables[0].draw(g);
		chairs[0].draw(g);

		orderMachine.draw(g);
		orderMonitor.draw(g);

		ArrayList<BaseNpc> npcs = NpcManager.getInstance().getAllEntities();
		for (BaseNpc npc : npcs)
		{
			npc.draw(g);
		}

		ArrayList<Player> players = PlayerManager.getInstance().getAllEntities();
		for (Player player : players)
		{
			player.draw(g);
		}
	}
}
