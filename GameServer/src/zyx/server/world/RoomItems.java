package zyx.server.world;

import org.lwjgl.util.vector.Vector3f;
import zyx.server.world.interactable.chef.ChefFridge;
import zyx.server.world.interactable.chef.ChefStove;
import zyx.server.world.interactable.chef.OrderMonitor;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.guests.GuestChair;
import zyx.server.world.interactable.player.OrderMachine;

public class RoomItems
{
	public static RoomItems instance;
	
	private ChefFridge[] fridges;
	private ChefStove[] stoves;
	private FoodTable[] foodTables;
	
	private DinnerTable[] dinnerTables;
	private GuestChair[] chairs;
	
	private OrderMachine orderMachine;
	private OrderMonitor orderMonitor;

	public RoomItems()
	{
		instance = this;
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
	
}
