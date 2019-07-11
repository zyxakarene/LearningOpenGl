package zyx.game.network.joining;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.joining.SetupConstants.*;
import zyx.net.data.ReadableDataArray;

public class SetupGameResponse extends BaseNetworkResponse<GameSetupVo>
{
	private static final GameSetupVo OUT = new GameSetupVo();
	
	public SetupGameResponse()
	{
		super(NetworkCommands.SETUP_GAME);
	}

	@Override
	protected GameSetupVo onMessageRecieved(ReadableDataObject data)
	{
		int playerCount = data.getInteger(PLAYER_COUNT);
		ReadableDataArray<ReadableDataObject> array = data.<ReadableDataObject>getArray(PLAYERS);
		
		OUT.players = new GameSetupPlayerInfo[playerCount];
		for (int i = 0; i < playerCount; i++)
		{
			ReadableDataObject playerData = array.get(i);
			
			GameSetupPlayerInfo playerInfo = new GameSetupPlayerInfo();
			playerInfo.id = playerData.getInteger(PLAYER_ID);
			playerInfo.name = playerData.getString(PLAYER_NAME);
			playerInfo.pos.x = playerData.getFloat(PLAYER_X);
			playerInfo.pos.y = playerData.getFloat(PLAYER_Y);
			playerInfo.pos.z = playerData.getFloat(PLAYER_Z);
			playerInfo.lookAt.x = playerData.getFloat(PLAYER_LOOK_X);
			playerInfo.lookAt.y = playerData.getFloat(PLAYER_LOOK_Y);
			playerInfo.lookAt.z = playerData.getFloat(PLAYER_LOOK_Z);
			
			OUT.players[i] = playerInfo;
		}
		
		return OUT;
	}

}
