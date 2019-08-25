package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.game.components.world.IItemHolder;
import zyx.utils.interfaces.IUpdateable;

public class ItemHolderHandler implements IUpdateable
{
	private HashMap<Integer, IItemHolder> holderMap;
	private ArrayList<IItemHolder> holderList;
	
	public ItemHolderHandler()
	{
		holderMap = new HashMap<>();
		holderList = new ArrayList<>();
	}
	
	public void addItemHolder(int uniqueId, IItemHolder holder)
	{
		holderMap.put(uniqueId, holder);
		holderList.add(holder);
	}
	
	public IItemHolder getById(int uniqueId)
	{
		return holderMap.get(uniqueId);
	}
	
	public void remove(int uniqueId)
	{
		IItemHolder character = holderMap.remove(uniqueId);
		holderList.remove(character);
		
		character.dispose();
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (IItemHolder character : holderList)
		{
			character.update(timestamp, elapsedTime);
		}
	}

	public void clean()
	{
		for (IItemHolder character : holderList)
		{
			character.dispose();
		}
		
		holderMap.clear();
		holderList.clear();
	}
}
