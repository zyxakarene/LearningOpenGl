package zyx.game.joining;

import zyx.game.joining.data.GameSetupVo;
import zyx.game.joining.data.GameSetupPlayerInfo;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.joining.SetupConstants.*;
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
		int playerCount = array.size();
		
		OUT.players = new GameSetupPlayerInfo[playerCount];
		for (int i = 0; i < playerCount; i++)
		{
			ReadableDataObject playerData = array.get(i);
			
			GameSetupPlayerInfo playerInfo = new GameSetupPlayerInfo();
			playerInfo.id = playerData.getInteger(CHARACTER_ID);
			playerInfo.name = playerData.getString(CHARACTER_NAME);
			playerInfo.pos.x = playerData.getFloat(CHARACTER_X);
			playerInfo.pos.y = playerData.getFloat(CHARACTER_Y);
			playerInfo.pos.z = playerData.getFloat(CHARACTER_Z);
			playerInfo.lookAt.x = playerData.getFloat(CHARACTER_LOOK_X);
			playerInfo.lookAt.y = playerData.getFloat(CHARACTER_LOOK_Y);
			playerInfo.lookAt.z = playerData.getFloat(CHARACTER_LOOK_Z);
			
			OUT.players[i] = playerInfo;
		}
		
		return OUT;
	}

}
