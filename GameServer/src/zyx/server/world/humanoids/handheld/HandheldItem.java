package zyx.server.world.humanoids.handheld;

import zyx.game.vo.HandheldItemType;
import zyx.server.controller.services.ItemService;
import zyx.server.utils.IUpdateable;

public abstract class HandheldItem implements IUpdateable
{

	private static int idCounter = 0;

	public final int id;
	public final HandheldItemType type;
	
	public boolean inUse;
	public int ownerId;

	public HandheldItem(HandheldItemType type, boolean invisible)
	{
		this.id = idCounter++;

		this.type = type;
		this.inUse = true;
		
		if (!invisible)
		{
			HandheldItemList.addItem(this);
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	public void dispose()
	{
		HandheldItemList.removeItem(this);
		ItemService.destroyItem(this);
	}
	
	public abstract void process();
	
	@Override
	public String toString()
	{
		String clazz = getClass().getSimpleName();
		String typeName = type.name().toLowerCase();
		return String.format("%s-%s[%s]", clazz, typeName, id);
	}
	
	public String getVisualName()
	{
		return type.name();
	}

}
