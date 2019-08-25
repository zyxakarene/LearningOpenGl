package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.world.World3D;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.items.GameItem;
import zyx.game.vo.HandheldItemType;

public class ItemHandler
{

	private World3D world;

	private HashMap<Integer, GameItem> itemMap;
	private ArrayList<GameItem> itemList;
	private ItemHolderHandler itemHolderHandler;

	public ItemHandler(ItemHolderHandler holderHandler)
	{
		world = World3D.instance;

		itemMap = new HashMap<>();
		itemList = new ArrayList<>();
		itemHolderHandler = holderHandler;
	}

	public void addItem(int uniqueId, GameItem item, int ownerId)
	{
		itemMap.put(uniqueId, item);
		itemList.add(item);

		IItemHolder itemHolder = itemHolderHandler.getById(ownerId);
		item.setOwnerId(ownerId);
		if (itemHolder != null)
		{
			itemHolder.hold(item);
		}
	}

	public void setOwner(int uniqueId, int ownerId)
	{
		GameItem item = itemMap.get(uniqueId);

		if (item != null)
		{
			int oldOwnerId = item.getOwnerId();
			IItemHolder oldHolder = itemHolderHandler.getById(oldOwnerId);
			if (oldHolder != null)
			{
				oldHolder.removeItem();
			}

			IItemHolder newHolder = itemHolderHandler.getById(ownerId);
			if (newHolder != null)
			{
				newHolder.hold(item);
			}
			item.setOwnerId(ownerId);
		}
	}

	public GameItem getItemById(int uniqueId)
	{
		return itemMap.get(uniqueId);
	}

	public void removeItem(int uniqueId)
	{
		GameItem item = itemMap.remove(uniqueId);
		if (item != null)
		{
			itemList.remove(item);
			item.dispose();
		}
	}

	public void clean()
	{
		for (GameItem item : itemList)
		{
			item.dispose();
		}

		itemMap.clear();
		itemList.clear();
	}

	public void setType(int uniqueId, HandheldItemType type)
	{
		GameItem item = itemMap.remove(uniqueId);
		item.setType(type);
	}
}
