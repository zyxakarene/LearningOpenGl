package zyx.game.world.player;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerPrintBillRequest extends BaseNetworkRequest
{
	public PlayerPrintBillRequest()
	{
		super(NetworkCommands.PLAYER_PRINT_BILL);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int playerId = (int) params[0];
		data.addInteger(PLAYER_ID, playerId);
	}
}
