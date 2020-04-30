package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.scene.SceneManager;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.items.GameItem;
import zyx.game.scene.game.DinerScene;

public class ItemHandler
{

	private HashMap<Integer, GameItem> itemMap;
	private ArrayList<GameItem> itemList;
	private ItemHolderHandler itemHolderHandler;
	private DinerScene scene;

	public ItemHandler(ItemHolderHandler holderHandler)
	{
		itemMap = new HashMap<>();
		itemList = new ArrayList<>();
		itemHolderHandler = holderHandler;
		
//TODO: Fix
//		scene = SceneManager.getInstance().<DinerScene>getSceneAs();
	}

	public void addItem(int uniqueId, GameItem item, int ownerId)
	{
		item.uniqueId = uniqueId;
		
		scene.addInteractableObject(item);
		
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
		setOwner(uniqueId, ownerId, null);
	}
	
	public void setOwner(int uniqueId, int ownerId, Vector3f position)
	{
		GameItem item = itemMap.get(uniqueId);

		if (item != null)
		{
			int oldOwnerId = item.getOwnerId();
			IItemHolder oldHolder = itemHolderHandler.getById(oldOwnerId);
			if (oldHolder != null)
			{
				oldHolder.removeItem(item);
			}

			IItemHolder newHolder = itemHolderHandler.getById(ownerId);
			if (newHolder != null)
			{
				newHolder.hold(item);
			}
			item.setOwnerId(ownerId);
			
			if (position != null)
			{
				item.setPosition(false, position);
			}
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
			scene.removeInteractableObject(item);
			
			IItemHolder currentOwner = itemHolderHandler.getById(item.getOwnerId());
			if (currentOwner != null)
			{
				currentOwner.removeItem(item);
			}
			
			itemList.remove(item);
			item.dispose();
		}
	}

	public void dispose()
	{
		if (itemList != null)
		{
			for (GameItem item : itemList)
			{
				item.dispose();
			}

			itemMap.clear();
			itemList.clear();
			
			itemList = null;
			itemMap = null;
		}
	}

	public <T extends Enum> void setSubType(int uniqueId, T type)
	{
		GameItem<T> item = itemMap.get(uniqueId);
		if (item != null)
		{
			item.setSubType(type);
		}
	}

	public void setItemInUse(int uniqueId, boolean inUse)
	{
		GameItem item = itemMap.get(uniqueId);
		if (item != null)
		{
			item.setInUse(inUse);
		}
	}
}
