package zyx.game.components.world.items;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.IInteractable;
import zyx.game.vo.HandheldItemType;

public abstract class GameItem extends SimpleMesh implements IInteractable
{
	public int uniqueId;
	
	private int currentOwnerId;
	
	protected HandheldItemType type;
	protected boolean inUse;
	
	public GameItem(HandheldItemType type)
	{
		this.type = type;
		inUse = true;
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
	
	public void setInUse(boolean value)
	{
		inUse = value;
	}
	
	protected abstract String getItemResource();

	@Override
	public boolean isInteractable()
	{
		return !inUse;
	}
}
