package zyx.server.world;

import java.awt.Graphics;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.interactable.chef.Fridge;
import zyx.server.world.interactable.chef.Stove;
import zyx.server.world.interactable.chef.Monitor;
import zyx.server.world.interactable.cleaner.DishWasher;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.common.player.IPlayerInteractable;
import zyx.server.world.interactable.floor.Floor;
import zyx.server.world.interactable.guests.Chair;
import zyx.server.world.interactable.guests.ExitPoint;
import zyx.server.world.interactable.player.OrderMachine;
import zyx.server.world.pathfanding.GraphBuilder;
import zyx.server.world.pathfanding.NodeGraph;

public class RoomItems implements IUpdateable
{

	public static RoomItems instance;

	public Fridge[] fridges;
	public Stove[] stoves;
	public FoodTable[] foodTables;

	public DinnerTable[] dinnerTables;
	public Chair[] chairs;

	public DishWasher dishWasher;
	public OrderMachine orderMachine;
	public Monitor orderMonitor;
	public Floor floor;

	public ExitPoint exitPoint;

	public NodeGraph graph;

	public RoomItems()
	{
		instance = this;

		floor = new Floor();
		
		fridges = new Fridge[1];
		fridges[0] = new Fridge();
		fridges[0].updatePosition(200, 25, 0);

		stoves = new Stove[1];
		stoves[0] = new Stove();
		stoves[0].updatePosition(250, 35, 0);

		foodTables = new FoodTable[1];
		foodTables[0] = new FoodTable();
		foodTables[0].updatePosition(200, 100, 0);

		chairs = new Chair[1];
		chairs[0] = new Chair();
		chairs[0].updatePosition(50, 150, 0);

		dinnerTables = new DinnerTable[1];
		dinnerTables[0] = new DinnerTable(chairs);
		dinnerTables[0].updatePosition(50, 100, 0);

		chairs[0].linkToTable(dinnerTables[0]);

		orderMachine = new OrderMachine();
		orderMachine.updatePosition(200, 200, 0);
		orderMonitor = new Monitor();
		orderMonitor.updatePosition(150, 35, 0);

		exitPoint = new ExitPoint();
		exitPoint.updatePosition(0, 0, 0);

		dishWasher = new DishWasher();
		dishWasher.updatePosition(200, 250, 0);

		graph = GraphBuilder.getGraph();
	}

	public Fridge getNearestFridge(Vector3f from)
	{
		return fridges[0];
	}

	public Monitor orderMonitor()
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
		dishWasher.update(timestamp, elapsedTime);
		
		floor.update(timestamp, elapsedTime);
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

		dishWasher.draw(g);
		
		floor.draw(g);

//		graph.draw(g);

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

	public OrderMachine getOrderMachine()
	{
		return orderMachine;
	}
	
	public Chair getChairWithGuest(Guest guest)
	{
		for (Chair chair : chairs)
		{
			if (chair.isCurrentGuestSitting() && chair.currentUser == guest)
			{
				return chair;
			}
		}
		
		return null;
	}

	public IPlayerInteractable getEntityWithItem(int itemId)
	{
		for (FoodTable table : foodTables)
		{
			if (table.containsItem(itemId))
			{
				return table;
			}
		}
		
		for (DinnerTable table : dinnerTables)
		{
			if (table.containsItem(itemId))
			{
				return table;
			}
		}
		
		if (floor.containsItem(itemId))
		{
			return floor;
		}
		
		return null;
	}

	public IPlayerInteractable getEntityWithId(int ownerId)
	{
		for (FoodTable table : foodTables)
		{
			if (table.id == ownerId)
			{
				return table;
			}
		}
		
		for (DinnerTable table : dinnerTables)
		{
			if (table.id == ownerId)
			{
				return table;
			}
		}
		
		if (floor.id == ownerId)
		{
			return floor;
		}
		
		return null;
	}
}
