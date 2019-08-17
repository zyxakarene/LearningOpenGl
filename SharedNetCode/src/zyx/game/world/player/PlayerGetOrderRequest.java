package zyx.game.world.player;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerGetOrderRequest extends BaseNetworkRequest
{
	public PlayerGetOrderRequest()
	{
		super(NetworkCommands.PLAYER_GET_ORDER);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int playerID = (int) params[0];
		int npcID = (int) params[1];
		
		data.addInteger(PLAYER_ID, playerID);
		data.addInteger(NPC_ID, npcID);
	}
}
