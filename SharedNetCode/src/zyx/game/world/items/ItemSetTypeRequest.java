package zyx.game.world.items;

import zyx.game.vo.HandheldItemType;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.items.ItemConstants.*;

public class ItemSetTypeRequest extends BaseNetworkRequest
{
	public ItemSetTypeRequest()
	{
		super(NetworkCommands.ITEM_SET_TYPE);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int itemID = (int) params[0];
		HandheldItemType type = (HandheldItemType) params[1];
		
		data.addInteger(ITEM_ID, itemID);
		data.addString(ITEM_TYPE, type.toString());
	}
}
