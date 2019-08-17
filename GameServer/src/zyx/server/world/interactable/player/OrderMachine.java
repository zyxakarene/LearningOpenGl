package zyx.server.world.interactable.player;

import java.awt.Color;
import java.util.ArrayList;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.handheld.food.DishType;
import zyx.server.world.humanoids.handheld.guests.GuestOrder;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.chef.Monitor;

public class OrderMachine extends BaseInteractableItem<Player>
{

	@Override
	public void interactWith(Player player)
	{
		GuestOrder order = (GuestOrder) player.heldItem();
		
		if (order != null)
		{
			player.removeItem(true);
			Monitor monitor = RoomItems.instance.orderMonitor();
			
			ArrayList<DishType> dishes = order.getDishes();
			for (DishType dish : dishes)
			{
				System.out.println("The dish " + dish + " was punched in");
				monitor.addDish(dish);
			}
		}
	}
	
	@Override
	public Color getColor()
	{
		return Color.RED;
	}
}
