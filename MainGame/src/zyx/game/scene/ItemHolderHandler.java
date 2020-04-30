package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.scene.SceneManager;
import zyx.game.components.world.IItemHolder;
import zyx.game.scene.game.DinerScene;
import zyx.utils.interfaces.IUpdateable;

public class ItemHolderHandler implements IUpdateable
{

	private HashMap<Integer, IItemHolder> holderMap;
	private ArrayList<IItemHolder> holderList;
	private DinerScene scene;

	public ItemHolderHandler()
	{
		holderMap = new HashMap<>();
		holderList = new ArrayList<>();
		
		scene = SceneManager.getInstance().<DinerScene>getSceneAs();
	}

	public void addItemHolder(int uniqueId, IItemHolder holder)
	{
		if (holder.isInteractable())
		{
			scene.addInteractableObject(holder);
		}

		holderMap.put(uniqueId, holder);
		holderList.add(holder);
	}

	public IItemHolder getById(int uniqueId)
	{
		return holderMap.get(uniqueId);
	}

	public void remove(int uniqueId)
	{
		IItemHolder holder = holderMap.remove(uniqueId);
		holderList.remove(holder);

		if (holder.isInteractable())
		{
			scene.removeInteractableObject(holder);
		}
		
		holder.dispose();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (IItemHolder holder : holderList)
		{
			holder.update(timestamp, elapsedTime);
		}
	}

	public void dispose()
	{
		if (holderList != null)
		{
			for (IItemHolder holder : holderList)
			{
				holder.dispose();
			}

			holderMap.clear();
			holderList.clear();

			holderList = null;
			holderMap = null;
		}
	}
}
