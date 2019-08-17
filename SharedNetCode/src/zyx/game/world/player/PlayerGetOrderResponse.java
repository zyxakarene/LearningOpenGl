package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;
import zyx.game.world.player.data.PlayerRequestData;

public class PlayerGetOrderResponse extends BaseNetworkResponse<PlayerRequestData>
{
	private static final PlayerRequestData OUT = PlayerRequestData.INSTANCE;

	public PlayerGetOrderResponse()
	{
		super(NetworkCommands.PLAYER_GET_ORDER);
	}

	@Override
	protected PlayerRequestData onMessageRecieved(ReadableDataObject data)
	{
		OUT.playerId = data.getInteger(PLAYER_ID);
		OUT.npcId = data.getInteger(NPC_ID);

		return OUT;
	}

}
