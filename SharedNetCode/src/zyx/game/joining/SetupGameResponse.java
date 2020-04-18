package zyx.game.joining;

import zyx.game.joining.data.GameSetupVo;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.joining.SetupConstants.*;
import zyx.game.vo.*;
import zyx.net.data.ReadableDataArray;

public class SetupGameResponse extends BaseNetworkResponse<GameSetupVo>
{

	private static final GameSetupVo OUT = GameSetupVo.INSTANCE;

	public SetupGameResponse()
	{
		super(NetworkCommands.SETUP_GAME);
	}

	@Override
	protected GameSetupVo onMessageRecieved(ReadableDataObject data)
	{
		ReadableDataArray<ReadableDataObject> array = data.<ReadableDataObject>getArray(CHARACTERS);
		ReadableDataArray<ReadableDataObject> furniture = data.<ReadableDataObject>getArray(FURNITURE);
		ReadableDataArray<ReadableDataObject> items = data.<ReadableDataObject>getArray(ITEMS);

		addPlayers(array);
		addFurniture(furniture);
		addItems(items);

		return OUT;
	}

	private void addPlayers(ReadableDataArray<ReadableDataObject> array)
	{
		int playerCount = array.size();

		OUT.characters.joinCount = playerCount;
		for (int i = 0; i < playerCount; i++)
		{
			ReadableDataObject playerData = array.get(i);

			OUT.characters.ids[i] = playerData.getInteger(ID);
			OUT.characters.names[i] = playerData.getString(NAME);
			OUT.characters.positions[i].x = playerData.getFloat(X);
			OUT.characters.positions[i].y = playerData.getFloat(Y);
			OUT.characters.positions[i].z = playerData.getFloat(Z);
			OUT.characters.lookAts[i].x = playerData.getFloat(LOOK_X);
			OUT.characters.lookAts[i].y = playerData.getFloat(LOOK_Y);
			OUT.characters.lookAts[i].z = playerData.getFloat(LOOK_Z);
			
			int genderId = playerData.getInteger(GENDER);
			int typeId = playerData.getInteger(TYPE);
			OUT.characters.genders[i] = Gender.getFromId(genderId);
			OUT.characters.types[i] = CharacterType.getFromId(typeId);
		}
	}

	private void addFurniture(ReadableDataArray<ReadableDataObject> array)
	{
		int furnitureCount = array.size();

		OUT.furniture.furnitureCount = furnitureCount;
		for (int i = 0; i < furnitureCount; i++)
		{
			ReadableDataObject furnitureData = array.get(i);

			OUT.furniture.ids[i] = furnitureData.getInteger(ID);
			OUT.furniture.positions[i].x = furnitureData.getFloat(X);
			OUT.furniture.positions[i].y = furnitureData.getFloat(Y);
			OUT.furniture.positions[i].z = furnitureData.getFloat(Z);
			OUT.furniture.lookAts[i].x = furnitureData.getFloat(LOOK_X);
			OUT.furniture.lookAts[i].y = furnitureData.getFloat(LOOK_Y);
			OUT.furniture.lookAts[i].z = furnitureData.getFloat(LOOK_Z);
			int type = furnitureData.getInteger(TYPE);
			OUT.furniture.types[i] = FurnitureType.getFromId(type);
		}
	}

	private void addItems(ReadableDataArray<ReadableDataObject> array)
	{
		int itemCount = array.size();

		OUT.items.itemCount = itemCount;
		for (int i = 0; i < itemCount; i++)
		{
			ReadableDataObject itemData = array.get(i);

			int type = itemData.getInteger(TYPE);
			int dish = itemData.getInteger(DISH);
			
			OUT.items.ids[i] = itemData.getInteger(ID);
			OUT.items.ownerIds[i] = itemData.getInteger(OWNER_ID);
			
			if (type > 0)
			{
				OUT.items.types[i] = HandheldItemType.getFromId(type);
			}
			
			if (dish > 0)
			{
				OUT.items.dishTypes[i] = DishType.getFromId(dish);
				OUT.items.dishSpoiled[i] = itemData.getBoolean(SPOILED);
			}
		}
	}
}
