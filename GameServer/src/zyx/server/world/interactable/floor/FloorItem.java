package zyx.server.world.interactable.floor;

import zyx.server.world.humanoids.handheld.HandheldItem;

public class FloorItem
{
	public final HandheldItem item;
	public final float x;
	public final float y;
	public final float z;

	public FloorItem(HandheldItem item, float x, float y, float z)
	{
		this.item = item;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
