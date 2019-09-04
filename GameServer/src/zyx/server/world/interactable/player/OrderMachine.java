package zyx.server.world.interactable.player;

import java.awt.Color;
import zyx.server.world.RoomItems;
import zyx.game.vo.DishType;
import zyx.game.vo.FurnitureType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.chef.Monitor;

public class OrderMachine extends BaseInteractableItem<Player>
{

	public OrderMachine()
	{
		super(FurnitureType.ORDER_MACHINE);
	}

	public void addOrder(DishType dish)
	{
		Monitor monitor = RoomItems.instance.orderMonitor();
		System.out.println("The dish " + dish + " was punched in");
		monitor.addDish(dish);
	}

	@Override
	public void interactWith(Player player)
	{
		throw new RuntimeException("Do not interact with the order machine. Use addOrder(DishType) please");
	}

	@Override
	public Color getColor()
	{
		return Color.RED;
	}
}
