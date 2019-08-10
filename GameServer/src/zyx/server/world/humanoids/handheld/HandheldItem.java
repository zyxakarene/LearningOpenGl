package zyx.server.world.humanoids.handheld;

import zyx.server.utils.IUpdateable;

public abstract class HandheldItem implements IUpdateable
{
	public HandheldItemType type;
	public boolean inUse;

	public HandheldItem(HandheldItemType initialType)
	{
		this.type = initialType;
		this.inUse = true;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}
	
	public abstract void process();

}
