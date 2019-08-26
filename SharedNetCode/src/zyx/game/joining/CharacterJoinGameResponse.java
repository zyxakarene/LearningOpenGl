package zyx.game.joining;

import static zyx.game.joining.SetupConstants.*;
import zyx.game.joining.data.CharacterJoinedData;
import zyx.net.data.ReadableDataArray;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class CharacterJoinGameResponse extends BaseNetworkResponse<CharacterJoinedData>
{
	private static final CharacterJoinedData OUT = CharacterJoinedData.INSTANCE;
	
	public CharacterJoinGameResponse()
	{
		super(NetworkCommands.CHARACTER_JOINED_GAME);
	}

	@Override
	protected CharacterJoinedData onMessageRecieved(ReadableDataObject data)
	{
		ReadableDataArray<ReadableDataObject> array = data.getArray(CHARACTERS);
		int joinCoint = array.size();
		OUT.joinCount = joinCoint;
		
		for (int i = 0; i < joinCoint; i++)
		{
			ReadableDataObject charData = array.get(i);
			OUT.ids[i] = charData.getInteger(ID);
			OUT.positions[i].x = charData.getFloat(X);
			OUT.positions[i].y = charData.getFloat(Y);
			OUT.positions[i].z = charData.getFloat(Z);
			OUT.lookAts[i].x = charData.getFloat(LOOK_X);
			OUT.lookAts[i].y = charData.getFloat(LOOK_Y);
			OUT.lookAts[i].z = charData.getFloat(LOOK_Z);
			OUT.names[i] = charData.getString(CHARACTER_NAME);
		}
		
		return OUT;
	}

}
