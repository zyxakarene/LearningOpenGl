package zyx.server.world.interactable.floor;

import zyx.server.world.humanoids.handheld.HandheldItem;

class FloorItem
{
	HandheldItem item;
	float x;
	float y;
	float z;

	public FloorItem(HandheldItem item, float x, float y, float z)
	{
		this.item = item;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
