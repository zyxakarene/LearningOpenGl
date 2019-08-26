package zyx.game.joining;

import zyx.game.joining.data.GameSetupVo;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.joining.SetupConstants.*;
import zyx.game.vo.FurnitureType;
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

		addPlayers(array);
		addFurniture(furniture);

		return OUT;
	}

	private void addPlayers(ReadableDataArray<ReadableDataObject> array)
	{
		int playerCount = array.size();

		OUT.players.joinCount = playerCount;
		for (int i = 0; i < playerCount; i++)
		{
			ReadableDataObject playerData = array.get(i);

			OUT.players.ids[i] = playerData.getInteger(ID);
			OUT.players.names[i] = playerData.getString(CHARACTER_NAME);
			OUT.players.positions[i].x = playerData.getFloat(X);
			OUT.players.positions[i].y = playerData.getFloat(Y);
			OUT.players.positions[i].z = playerData.getFloat(Z);
			OUT.players.lookAts[i].x = playerData.getFloat(LOOK_X);
			OUT.players.lookAts[i].y = playerData.getFloat(LOOK_Y);
			OUT.players.lookAts[i].z = playerData.getFloat(LOOK_Z);
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
			String type = furnitureData.getString(FURNITURE_TYPE);
			OUT.furniture.types[i] = FurnitureType.valueOf(type);
		}
	}

}
