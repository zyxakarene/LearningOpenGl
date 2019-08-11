package zyx.server.world.interactable.common;

import java.util.ArrayList;
import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.common.player.PlayerInteractable;
import zyx.server.world.interactable.common.player.PlayerInteraction;

public abstract class CommonTable<User extends HumanoidEntity> extends BaseInteractableItem<User> implements IUpdateable, PlayerInteractable
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
	public final void interactWith(Player player, PlayerInteraction interaction)
	{
		if (interaction.isTake())
		{
			onPlayerTake(player, interaction.itemIdToTake);
		}
		else if (interaction.isGive())
		{
			onPlayerGive(player, interaction.itemToGive);
		}
	}
	
	public boolean tryAddItem(HandheldItem item)
	{
		if (itemsOnTable.size() < maxItemsOnTable)
		{
			//There is room on the table, so put it down
			itemsOnTable.add(item);
			return true;
		}
		
		return false;
	}

	protected HandheldItem removeItemById(int id)
	{
		HandheldItem item;
		
		int len = itemsOnTable.size();
		for (int i = 0; i < len; i++)
		{
			item = itemsOnTable.get(i);
			
			if (!item.inUse && item.id == id)
			{
				itemsOnTable.remove(i);
				return item;
			}
		}
		
		return null;
	}
	
	@Override
	public void interactWith(User user)
	{
		HandheldItem heldItem = user.heldItem();

		if (heldItem != null)
		{
			//User holding an item

			boolean wasAdded = tryAddItem(heldItem);
			if (wasAdded)
			{
				user.removeItem();
			}
		}
		else
		{
			//User has no items in hand

			for (HandheldItem tableItem : itemsOnTable)
			{
				if (!tableItem.inUse && user.wantsToHold(tableItem))
				{
					user.pickupItem(tableItem);
				}
			}
		}
	}

	protected void onPlayerTake(Player player, int itemId)
	{
		if (player.canHoldItem())
		{
			HandheldItem item = removeItemById(itemId);

			if (item != null)
			{
				player.pickupItem(item);
			}
		}
	}

	protected void onPlayerGive(Player player, HandheldItem itemToGive)
	{
	}
}
