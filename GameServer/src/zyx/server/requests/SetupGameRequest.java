package zyx.server.requests;

import java.util.ArrayList;
import zyx.net.data.WriteableDataArray;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;
import static zyx.game.joining.SetupConstants.*;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.HandheldItemList;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.interactable.BaseInteractableItem;

public class SetupGameRequest extends BaseNetworkRequest
{

	public SetupGameRequest()
	{
		super(NetworkCommands.SETUP_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Player player = (Player) params[0];

		ArrayList<Player> allPlayers = PlayerManager.getInstance().getAllEntities();
		ArrayList<BaseNpc> allNpcs = NpcManager.getInstance().getAllEntities();

		WriteableDataArray<WriteableDataObject> playerDataArray = new WriteableDataArray(WriteableDataObject.class);
		WriteableDataArray<WriteableDataObject> furnitureDataArray = new WriteableDataArray(WriteableDataObject.class);
		WriteableDataArray<WriteableDataObject> itemDataArray = new WriteableDataArray(WriteableDataObject.class);
		addFromList(allPlayers, player, playerDataArray);
		addFromList(allNpcs, player, playerDataArray);
		addFurniture(furnitureDataArray);
		addItems(itemDataArray);

		data.addArray(CHARACTERS, playerDataArray);
		data.addArray(FURNITURE, furnitureDataArray);
		data.addArray(ITEMS, itemDataArray);
	}

	protected void addFromList(ArrayList<? extends HumanoidEntity> entities, Player player, WriteableDataArray<WriteableDataObject> dataArray)
	{
		for (HumanoidEntity entity : entities)
		{
			if (entity != player)
			{
				WriteableDataObject playerData = new WriteableDataObject();
				playerData.addInteger(ID, entity.id);
				playerData.addString(NAME, entity.name);
				playerData.addFloat(X, entity.x);
				playerData.addFloat(Y, entity.y);
				playerData.addFloat(Z, entity.z);
				playerData.addFloat(LOOK_X, entity.lx);
				playerData.addFloat(LOOK_Y, entity.ly);
				playerData.addFloat(LOOK_Z, entity.lz);

				dataArray.add(playerData);
			}
		}
	}

	private void addFurniture(WriteableDataArray<WriteableDataObject> dataArray)
	{
		ArrayList<BaseInteractableItem> furnitureItems = RoomItems.instance.getAllItems();

		for (BaseInteractableItem item : furnitureItems)
		{
			WriteableDataObject furnitureData = new WriteableDataObject();
			furnitureData.addInteger(ID, item.id);
			furnitureData.addFloat(X, item.x);
			furnitureData.addFloat(Y, item.y);
			furnitureData.addFloat(Z, item.z);
			furnitureData.addFloat(LOOK_X, item.lx);
			furnitureData.addFloat(LOOK_Y, item.ly);
			furnitureData.addFloat(LOOK_Z, item.lz);
			furnitureData.addString(TYPE, item.type.toString());

			dataArray.add(furnitureData);
		}
	}

	private void addItems(WriteableDataArray<WriteableDataObject> dataArray)
	{
		ArrayList<HandheldItem> heldItem = HandheldItemList.getAllItems();
		
		for (HandheldItem item : heldItem)
		{
			WriteableDataObject itemData = new WriteableDataObject();
			itemData.addInteger(ID, item.id);
			itemData.addInteger(OWNER_ID, item.ownerId);
			itemData.addString(TYPE, item.type.toString());
			
			if (item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) item;
				itemData.addString(DISH, food.dish.toString());
				itemData.addBoolean(SPOILED, food.isSpoiled());
			}

			dataArray.add(itemData);
		}
	}

}
