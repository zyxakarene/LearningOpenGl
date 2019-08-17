package zyx.game.world.items;

import zyx.net.io.controllers.NetworkCommands;

public class ItemCreateBillRequest extends ItemCreateRequest
{
	public ItemCreateBillRequest()
	{
		super(NetworkCommands.ITEM_CREATE_BILL);
	}
}
