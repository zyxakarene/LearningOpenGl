package zyx.game.world.player;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;
import zyx.game.world.player.data.PlayerRequestData;

public class PlayerGiveBillResponse extends BaseNetworkResponse<PlayerRequestData>
{

	private static final PlayerRequestData OUT = PlayerRequestData.INSTANCE;

	public PlayerGiveBillResponse()
	{
		super(NetworkCommands.PLAYER_GIVE_BILL);
	}

	@Override
	protected PlayerRequestData onMessageRecieved(ReadableDataObject data)
	{
		OUT.playerId = data.getInteger(PLAYER_ID);
		OUT.ownerId = data.getInteger(OWNER_ID);

		return OUT;
	}

}
