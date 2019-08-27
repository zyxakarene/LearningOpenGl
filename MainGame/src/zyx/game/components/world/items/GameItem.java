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
		if (type == HandheldItemType.DIRTY_PLATE)
		{
			System.out.println(getPosition(true, null));
			System.out.println(getPosition(false, null));
		}
	}
	
	protected abstract String getItemResource();

}
