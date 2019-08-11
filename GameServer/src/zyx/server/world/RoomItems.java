package zyx.server.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.server.utils.IUpdateable;
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

		stoves = new ChefStove[1];
		stoves[0] = new ChefStove();

		foodTables = new FoodTable[1];
		foodTables[0] = new FoodTable();

		chairs = new GuestChair[1];
		chairs[0] = new GuestChair();

		dinnerTables = new DinnerTable[1];
		dinnerTables[0] = new DinnerTable(chairs);

		chairs[0].linkToTable(dinnerTables[0]);

		orderMachine = new OrderMachine();
		orderMonitor = new OrderMonitor();

		exitPoint = new GuestExitPoint();
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
}
