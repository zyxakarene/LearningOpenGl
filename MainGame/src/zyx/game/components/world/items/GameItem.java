package zyx.game.components.world.items;

import zyx.game.components.SimpleMesh;
import zyx.game.components.world.interactable.IInteractable;
import zyx.game.vo.HandheldItemType;

public abstract class GameItem<TSubType extends Enum> extends SimpleMesh implements IInteractable
{
	public int uniqueId;
	
	private int currentOwnerId;
	
	private final HandheldItemType type;
	protected boolean inUse;
	protected TSubType subType;
	
	public GameItem(HandheldItemType type, TSubType subType)
	{
		this.type = type;
		this.subType = subType;
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
	
	public void setInUse(boolean value)
	{
		inUse = value;
	}

	public TSubType getSubType()
	{
		return subType;
	}

	public void setSubType(TSubType subType)
	{
		this.subType = subType;
	}
	
	protected abstract String getItemResource();

	@Override
	public boolean isInteractable()
	{
		return !inUse;
	}
}
