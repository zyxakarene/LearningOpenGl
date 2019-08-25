package zyx.game.joining;

import zyx.game.joining.data.GameSetupVo;
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
		
		OUT.players.joinCount = playerCount;
		for (int i = 0; i < playerCount; i++)
		{
			ReadableDataObject playerData = array.get(i);
			
			OUT.players.ids[i] = playerData.getInteger(CHARACTER_ID);
			OUT.players.names[i] = playerData.getString(CHARACTER_NAME);
			OUT.players.positions[i].x = playerData.getFloat(CHARACTER_X);
			OUT.players.positions[i].y = playerData.getFloat(CHARACTER_Y);
			OUT.players.positions[i].z = playerData.getFloat(CHARACTER_Z);
			OUT.players.lookAts[i].x = playerData.getFloat(CHARACTER_LOOK_X);
			OUT.players.lookAts[i].y = playerData.getFloat(CHARACTER_LOOK_Y);
			OUT.players.lookAts[i].z = playerData.getFloat(CHARACTER_LOOK_Z);
		}
		
		return OUT;
	}

}
