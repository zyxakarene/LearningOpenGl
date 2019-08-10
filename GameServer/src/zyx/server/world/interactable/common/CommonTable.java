package zyx.server.world.interactable.common;

import java.util.ArrayList;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class CommonTable<User extends HumanoidEntity> extends BaseInteractableItem<User> implements IUpdateable
{
	
	private ArrayList<HandheldItem> itemsOnTable;
	private final int maxItemsOnTable;

	public CommonTable(int maxItemsOnTable)
	{
		itemsOnTable = new ArrayList<>();
		this.maxItemsOnTable = maxItemsOnTable;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (HandheldItem tableItem : itemsOnTable)
		{
			tableItem.update(timestamp, elapsedTime);
		}
	}
	
	@Override
	public void interactWith(User user)
	{
		HandheldItem heldItem = user.heldItem();
		
		if (heldItem != null)
		{
			//User holding an item
			
			if (itemsOnTable.size() < maxItemsOnTable)
			{
				//There is room on the table, so put it down
				itemsOnTable.add(heldItem);
				user.removeItem();
			}
		}
		else
		{
			//User has no items in hand
			
			for (HandheldItem tableItem : itemsOnTable)
			{
				if (!tableItem.inUse)
				{
					user.pickupItem(tableItem);
				}
			}
		}
	}
}
