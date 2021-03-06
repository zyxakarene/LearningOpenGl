package zyx.game.world.player;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerGiveBillRequest extends BaseNetworkRequest
{
	public PlayerGiveBillRequest()
	{
		super(NetworkCommands.PLAYER_GIVE_BILL);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int playerID = (int) params[0];
		int ownerID = (int) params[1];
		
		data.addInteger(PLAYER_ID, playerID);
		data.addInteger(OWNER_ID, ownerID);
	}
}
