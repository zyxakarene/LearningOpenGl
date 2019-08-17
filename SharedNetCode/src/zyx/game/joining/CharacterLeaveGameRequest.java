package zyx.game.joining;

import static zyx.game.joining.SetupConstants.*;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class CharacterLeaveGameRequest extends BaseNetworkRequest
{

	public CharacterLeaveGameRequest()
	{
		super(NetworkCommands.CHARACTER_LEFT_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		int id = (int) params[0];
		
		data.addInteger(CHARACTER_ID, id);
	}

}
