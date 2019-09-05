package zyx.server.controller.services;

import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.game.vo.HandheldItemType;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.handheld.guests.BillItem;

public class ItemService
{

	public static void createBill(BillItem bill, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_CREATE_BILL, bill.id, ownerId);
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

	public static void spoilFood(FoodItem food)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SPOIL_FOOD, food.id);
	}
}
