package zyx.game.network.services;

import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.characters.GameCharacter;
import zyx.game.components.world.furniture.BaseFurnitureItem;
import zyx.game.components.world.furniture.Table;
import zyx.game.components.world.items.GameItem;
import zyx.game.models.GameModels;
import zyx.game.vo.DishType;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;

public class PlayerService
{

	public static void enterOrder(DishType dish)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_ENTER_ORDER, dish.id);
	}

	public static void getOrder(GameCharacter character)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_GET_ORDER, GameModels.player.playerId, character.getUniqueId());
	}

	public static void giveBill(BaseFurnitureItem table)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_GIVE_BILL, GameModels.player.playerId, table.getUniqueId());
	}

	public static void giveItem(IItemHolder container)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_GIVE_ITEM, GameModels.player.playerId, container.getUniqueId());
	}

	public static void interactWith(BaseFurnitureItem furniture)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_INTERACT_WITH, GameModels.player.playerId, furniture.getUniqueId());
	}

	public static void pickupItem(GameItem item)
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_PICKUP_ITEM, GameModels.player.playerId, item.uniqueId);
	}

	public static void printBill()
	{
		NetworkChannel.sendRequest(NetworkCommands.PLAYER_PRINT_BILL, GameModels.player.playerId);
	}

}
