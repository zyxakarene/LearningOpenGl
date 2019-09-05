package zyx.game.joining;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.joining.SetupConstants.*;

public class CharacterLeaveGameResponse extends BaseNetworkResponse<Integer>
{
	public CharacterLeaveGameResponse()
	{
		super(NetworkCommands.CHARACTER_LEFT_GAME);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		return data.getInteger(ID);
	}

}
