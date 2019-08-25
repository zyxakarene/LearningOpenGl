package zyx.game.components.world.items;

import zyx.game.components.SimpleMesh;
import zyx.game.vo.HandheldItemType;

public abstract class GameItem extends SimpleMesh
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

}
