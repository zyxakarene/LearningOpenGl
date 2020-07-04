package zyx.server.world.interactable.floor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import zyx.game.vo.FurnitureType;
import zyx.server.controller.services.ItemService;
import zyx.server.utils.Distance;
import zyx.server.utils.IUpdateable;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.handheld.food.FoodItem;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.cleaner.CleanerBehaviorType;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.interactable.BaseInteractableItem;
import zyx.server.world.interactable.common.ICleanable;
import zyx.server.world.interactable.common.player.IPlayerInteractable;
import zyx.server.world.interactable.common.player.PlayerInteraction;

public class Floor extends BaseInteractableItem<Cleaner> implements IPlayerInteractable, IUpdateable, ICleanable
{
	private ArrayList<FloorItem> floorItems;

	public Floor()
	{
		super(FurnitureType.FLOOR);

		floorItems = new ArrayList<>();
	}

	public void itemDropped(HandheldItem item, HumanoidEntity prevOwner)
	{
		FloorItem floorItem = new FloorItem(item, prevOwner.x, prevOwner.y, prevOwner.z);
		floorItems.add(floorItem);

		item.ownerId = id;
		item.inUse = false;
		
		ItemService.dropOnFloor(item, id, prevOwner.x, prevOwner.y, prevOwner.z);
	}

	public HandheldItem itemTaken(int itemId)
	{
		FloorItem foundItem = null;

		for (FloorItem floorItem : floorItems)
		{
			if (floorItem.item.id == itemId)
			{
				foundItem = floorItem;
				break;
			}
		}

		if (foundItem != null)
		{
			floorItems.remove(foundItem);
			foundItem.item.ownerId = -1;
			return foundItem.item;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void interactWith(Player player, PlayerInteraction interaction)
	{
		if (interaction.isGive())
		{
			HandheldItem heldItem = player.heldItem();
			if (heldItem != null)
			{
				player.removeItemSilent();
				itemDropped(heldItem, player);
			}
		}
		else if (player.canHoldItem())
		{
			HandheldItem takenItem = itemTaken(interaction.itemIdToTake);
			player.pickupItem(takenItem);
		}
	}

	@Override
	protected void onDraw(Graphics g)
	{
		for (FloorItem floorItem : floorItems)
		{
			HandheldItem heldItem = floorItem.item;
			int xPos = (int) (floorItem.x);
			int yPos = (int) (floorItem.y);

			g.setColor(Color.MAGENTA);
			g.fillRect(xPos, yPos, 10, 10);

			g.setColor(Color.WHITE);
			g.fillRect(xPos - 30, yPos - 8, 60, 8);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 8));
			g.drawString(heldItem.getVisualName(), xPos - 30, yPos - 1);
			g.drawRect(xPos - 31, yPos - 9, 61, 9);
		}
	}

	@Override
	public Color getColor()
	{
		return Color.WHITE;
	}

	@Override
	public int getSize()
	{
		return 0;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (FloorItem floorItem : floorItems)
		{
			floorItem.item.update(timestamp, elapsedTime);
		}
	}

	public boolean containsItem(int itemId)
	{
		for (FloorItem floorItem : floorItems)
		{
			if (floorItem.item.id == itemId)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canBeCleaned()
	{
		for (FloorItem floorItem : floorItems)
		{
			if (floorItem.item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) floorItem.item;
				if (food.cleanableByCleaner())
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void clean(Cleaner cleaner)
	{
		float closestDistance = Float.MAX_VALUE;
		FoodItem closestItem = null;

		for (FloorItem floorItem : floorItems)
		{
			if (floorItem.item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) floorItem.item;
				if (food.cleanableByCleaner())
				{
					float distance = Distance.between(cleaner, floorItem.x, floorItem.y, floorItem.z);
					if (distance < closestDistance)
					{
						closestDistance = distance;
						closestItem = food;
					}
				}
			}
		}

		if (closestItem != null && closestDistance <= 10)
		{
			itemTaken(closestItem.id);

			cleaner.pickupItem(closestItem);
			cleaner.requestBehavior(CleanerBehaviorType.GOING_TO_DISH_WASHER, RoomItems.instance.dishWasher);
		}
		else
		{
			cleaner.requestBehavior(CleanerBehaviorType.GOING_TO_IDLE);
		}
	}

	public FloorItem getCleanableItem()
	{
		for (FloorItem floorItem : floorItems)
		{
			if (floorItem.item instanceof FoodItem)
			{
				FoodItem food = (FoodItem) floorItem.item;
				if (food.cleanableByCleaner())
				{
					return floorItem;
				}
			}
		}

		return null;
	}

	@Override
	public void interactWith(Cleaner user)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
