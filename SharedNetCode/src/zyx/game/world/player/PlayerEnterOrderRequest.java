package zyx.game.world.player;

import zyx.game.vo.DishType;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.player.PlayerRequestConstants.*;

public class PlayerEnterOrderRequest extends BaseNetworkRequest
{
	public PlayerEnterOrderRequest()
	{
		super(NetworkCommands.PLAYER_ENTER_ORDER);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		DishType dish = (DishType) params[0];
		data.addString(DISH_TYPE, dish.toString());
	}
}
