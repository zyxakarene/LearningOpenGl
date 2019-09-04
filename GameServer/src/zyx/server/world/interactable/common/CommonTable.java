package zyx.server.world.interactable.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.ItemService;
import zyx.server.utils.IUpdateable;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.cleaner.CleanerBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.common.player.IPlayerInteractable;

public abstract class CommonTable<User extends HumanoidEntity> extends BaseInteractableItem<User> implements IUpdateable, IPlayerInteractable, ICleanable
{

	private ArrayList<HandheldItem> itemsOnTable;
	private final int maxItemsOnTable;

	public CommonTable(int maxItemsOnTable, FurnitureType type)
	{
		super(type);
		itemsOnTable = new ArrayList<>();
		this.maxItemsOnTable = maxItemsOnTable;
	}

	public int debug_GetFirstItemOnTable()
	{
		return itemsOnTable.isEmpty() ? -1 : itemsOnTable.get(0).id;
	}

	public boolean containsItem(int itemId)
	{
		for (HandheldItem item : itemsOnTable)
		{
			if (item.id == itemId)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canCarryMoreItems()
	{
		return itemsOnTable.size() < maxItemsOnTable;
	}

	public boolean isEmpty()
	{
		return itemsOnTable.isEmpty();
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
			onPlayerGive(player);
		}
	}

	public boolean tryAddItem(HandheldItem item)
	{
		if (itemsOnTable.size() < maxItemsOnTable && canAcceptItem(item))
		{
			System.out.println(item + " was placed on " + this);
			//There is room on the table, so put it down
			item.inUse = false;
			itemsOnTable.add(item);

			ItemService.setOwner(item, id);

			return true;
		}

		return false;
	}

	protected boolean canAcceptItem(HandheldItem item)
	{
		return true;
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
				System.out.println(item + " was removed from " + this);
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
				user.removeItemSilent();
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

	protected void onPlayerGive(Player player)
	{
	}

	@Override
	protected void onDraw(Graphics g)
	{
		super.onDraw(g);

		int offsetY = 0;
		for (HandheldItem heldItem : itemsOnTable)
		{
			int size = getSize();
			int sizeHalf = size / 2;

			int xPos = (int) (x - sizeHalf);
			int yPos = (int) (y - sizeHalf) + offsetY;

			g.setColor(Color.MAGENTA);
			g.fillRect(xPos, yPos, 10, 10);

			g.setColor(Color.WHITE);
			g.fillRect(xPos - 30, yPos - 8, 60, 8);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 8));
			g.drawString(heldItem.getVisualName(), xPos - 30, yPos - 1);
			g.drawRect(xPos - 31, yPos - 9, 61, 9);

			offsetY += 10;
		}
	}

	@Override
	public void clean(Cleaner cleaner)
	{
		FoodItem itemToClean = null;

		for (HandheldItem item : itemsOnTable)
		{
			if (item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) item;
				if (food.cleanableByCleaner())
				{
					itemToClean = food;
					break;
				}
			}
		}

		if (itemToClean != null)
		{
			removeItemById(itemToClean.id);
			cleaner.pickupItem(itemToClean);
			cleaner.requestBehavior(CleanerBehaviorType.GOING_TO_DISH_WASHER, RoomItems.instance.dishWasher);

		}
		else
		{
			cleaner.requestBehavior(CleanerBehaviorType.GOING_TO_IDLE);
		}
	}

	@Override
	public boolean canBeCleaned()
	{
		for (HandheldItem item : itemsOnTable)
		{
			if (item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) item;
				if (food.cleanableByCleaner())
				{
					return true;
				}
			}
		}
		return false;
	}
}
