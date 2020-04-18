package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerPrintBillResponse extends BaseNetworkResponse<Integer>
{

	public PlayerPrintBillResponse()
	{
		super(NetworkCommands.PLAYER_PRINT_BILL);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int playerId = data.getInteger(PLAYER_ID);		
		return playerId;
	}

}
