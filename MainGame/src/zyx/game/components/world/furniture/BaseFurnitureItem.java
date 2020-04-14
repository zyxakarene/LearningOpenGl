package zyx.game.components.world.furniture;

import java.util.ArrayList;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.items.GameItem;
import zyx.utils.interfaces.IPhysbox;

public abstract class BaseFurnitureItem<V extends SimpleMesh> extends GameObject implements IItemHolder
{

	protected ArrayList<GameItem> items;

	protected V view;

	public BaseFurnitureItem(boolean animated)
	{
		if (animated)
		{
			view = (V) new AnimatedMesh();
		}
		else
		{
			view = (V) new SimpleMesh();
		}

		addChild(view);
		
		items = new ArrayList<>();
	}

	public void load(FurnitureSetupVo vo)
	{
		String resource = getResource();
		view.load(resource);

		setPosition(false, vo.pos);
		lookAt(vo.look);
	}

	@Override
	public void hold(GameItem item)
	{
		items.add(item);
		onGotItem(item);
	}

	@Override
	public void removeItem(GameItem item)
	{
		items.remove(item);
		onLostItem(item);
	}
	
	@Override
	public IPhysbox getInteractionPhysbox()
	{
		return view;
	}
	
	@Override
	public boolean isInteractable()
	{
		return false;
	}

	protected void onGotItem(GameItem item)
	{
	}

	protected void onLostItem(GameItem item)
	{
	}

	protected abstract String getResource();

}
