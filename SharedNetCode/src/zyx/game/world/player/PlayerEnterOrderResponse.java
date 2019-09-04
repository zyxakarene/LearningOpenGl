package zyx.game.world.player;

import zyx.game.vo.DishType;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;
import zyx.game.world.player.data.PlayerRequestData;

public class PlayerEnterOrderResponse extends BaseNetworkResponse<DishType>
{

	private static final PlayerRequestData OUT = PlayerRequestData.INSTANCE;

	public PlayerEnterOrderResponse()
	{
		super(NetworkCommands.PLAYER_ENTER_ORDER);
	}

	@Override
	protected DishType onMessageRecieved(ReadableDataObject data)
	{
		String dish = data.getString(DISH_TYPE);
		DishType result = DishType.valueOf(dish);
		
		return result;
	}

}
