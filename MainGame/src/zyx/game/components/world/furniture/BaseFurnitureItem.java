package zyx.game.components.world.furniture;

import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.items.GameItem;

public abstract class BaseFurnitureItem extends GameObject implements IItemHolder
{
	protected GameItem item;
	
	protected SimpleMesh view;

	public BaseFurnitureItem(boolean animated)
	{
		if (animated)
		{
			view = new AnimatedMesh();
		}
		else
		{
			view = new SimpleMesh();
		}
	}
	
	protected void load(String resource)
	{
		view.load(resource);
	}
	
	@Override
	public void hold(GameItem item)
	{
		this.item = item;
		onGotItem();
	}

	@Override
	public void removeItem()
	{
		onLostItem();
		item = null;
	}

	protected void onGotItem()
	{
	}

	protected void onLostItem()
	{
	}

}
