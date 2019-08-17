package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;
import zyx.game.world.player.data.PlayerRequestData;

public class PlayerPickupResponse extends BaseNetworkResponse<PlayerRequestData>
{

	private static final PlayerRequestData OUT = PlayerRequestData.INSTANCE;

	public PlayerPickupResponse()
	{
		super(NetworkCommands.PLAYER_PICKUP_ITEM);
	}

	@Override
	protected PlayerRequestData onMessageRecieved(ReadableDataObject data)
	{
		OUT.itemId = data.getInteger(ITEM_ID);
		OUT.playerId = data.getInteger(PLAYER_ID);

		return OUT;
	}

}
