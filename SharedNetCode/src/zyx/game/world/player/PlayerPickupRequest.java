package zyx.game.world.player;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerPickupRequest extends BaseNetworkRequest
{
	public PlayerPickupRequest()
	{
		super(NetworkCommands.PLAYER_PICKUP_ITEM);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		int playerId = (int) params[1];
		
		data.addInteger(ITEM_ID, itemID);
		data.addInteger(PLAYER_ID, playerId);
	}
}
