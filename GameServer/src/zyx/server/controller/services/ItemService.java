package zyx.server.controller.services;

import zyx.game.vo.FoodState;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.handheld.HandheldItem;
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
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_CREATE_FOOD, food.id, ownerId, food.dish);
	}

	public static void destroyItem(HandheldItem item)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_DESTROY, item.id);
	}

	public static void setOwner(HandheldItem item, int ownerId)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SET_OWNER, item.id, ownerId);
	}

	public static void dropOnFloor(HandheldItem item, int floorId, float x, float y, float z)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_PUT_ON_FOOR, item.id, floorId, x, y, z);
	}

	public static void setFoodState(HandheldItem item, FoodState state)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SET_FOOD_STATE, item.id, state.id);
	}

	public static void spoilFood(FoodItem food)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SPOIL_FOOD, food.id);
	}

	public static void setInUse(HandheldItem item, boolean inUse)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.ITEM_SET_IN_USE, item.id, inUse);
	}
}
