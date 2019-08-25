package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.DishType;
import zyx.game.vo.HandheldItemType;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.humanoids.handheld.guests.GuestOrder;

public class ItemService
{

	public static void createBill(BillItem bill, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_CREATE_BILL, bill.id, ownerId);
	}
	
	public static void createOrders(GuestOrder orders, DishType type, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_CREATE_ORDER, orders.id, ownerId, type.name());
	}
	
	public static void addToOrders(GuestOrder orders, DishType type)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_ADD_ORDER, orders.id, type.name());
	}
	
	public static void createFood(FoodItem food, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_CREATE_FOOD, food.id, ownerId, food.dish.name());
	}

	public static void destroyItem(HandheldItem item)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_DESTROY, item.id);
	}

	public static void setOwner(HandheldItem item, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SET_OWNER, item.id, ownerId);
	}

	public static void setType(HandheldItem item, HandheldItemType type)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SET_TYPE, item.id, type);
	}
}
