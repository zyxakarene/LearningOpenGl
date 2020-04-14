package zyx.game.components.world.items;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.IInteractable;
import zyx.game.vo.HandheldItemType;
import zyx.utils.interfaces.IPhysbox;

public abstract class GameItem extends SimpleMesh implements IInteractable
{
	private int currentOwnerId;
	protected HandheldItemType type;
	
	public GameItem(HandheldItemType type)
	{
		this.type = type;
	}

	public void load()
	{
		load(getItemResource());
	}
	
	public int getOwnerId()
	{
		return currentOwnerId;
	}

	public void setOwnerId(int ownerId)
	{
		currentOwnerId = ownerId;
	}

	public void setType(HandheldItemType type)
	{
		this.type = type;
	}
	
	protected abstract String getItemResource();

	@Override
	public boolean isInteractable()
	{
		return true;
	}

	@Override
	public IPhysbox getInteractionPhysbox()
	{
		return this;
	}
}
