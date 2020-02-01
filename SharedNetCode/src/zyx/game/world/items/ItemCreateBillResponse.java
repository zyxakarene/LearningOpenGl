package zyx.game.world.items;

import zyx.net.io.controllers.NetworkCommands;

public class ItemCreateBillResponse extends ItemCreateResponse
{
	public ItemCreateBillResponse()
	{
		super(NetworkCommands.ITEM_CREATE_BILL);
	}

}
