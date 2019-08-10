package zyx.server.world.interactable.player;

import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.handheld.food.DishType;
import zyx.server.world.humanoids.handheld.guests.GuestOrder;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.chef.OrderMonitor;

public class OrderMachine extends BaseInteractableItem<Player>
{

	@Override
	public void interactWith(Player player)
	{
		GuestOrder order = (GuestOrder) player.heldItem();
		
		if (order != null)
		{
			OrderMonitor monitor = RoomItems.instance.orderMonitor();
			
			DishType[] dishes = order.getDishes();
			for (DishType dish : dishes)
			{
				monitor.addDish(dish);
			}
		}
	}
}
