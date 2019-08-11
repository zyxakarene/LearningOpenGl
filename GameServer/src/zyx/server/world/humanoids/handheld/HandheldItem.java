package zyx.server.world.humanoids.handheld;

import zyx.server.utils.IUpdateable;

public abstract class HandheldItem implements IUpdateable
{

	private static int idCounter = 0;

	public final int id;
	public HandheldItemType type;
	public boolean inUse;

	public HandheldItem(HandheldItemType initialType)
	{
		this.id = idCounter++;

		this.type = initialType;
		this.inUse = true;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	public abstract void process();
	
	@Override
	public String toString()
	{
		String clazz = getClass().getSimpleName();
		return String.format("%s[%s]", clazz, id);
	}

}
