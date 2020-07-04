package zyx.server.world.interactable.floor;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.FurnitureType;
import zyx.server.world.humanoids.handheld.HandheldItem;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.interactable.BaseInteractableItem;

public class FloorItem extends BaseInteractableItem<Cleaner>
{
	public final HandheldItem item;

	public FloorItem(HandheldItem item, float x, float y, float z)
	{
		super(FurnitureType.FLOOR_ITEM);
		
		this.item = item;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void getUsingPosition(Vector3f pos, Vector3f lookPos)
	{
		pos.x = x;
		pos.y = y;
		pos.z = z;
		
		lookPos.x = x+1;
		lookPos.y = y;
		lookPos.z = z;
	}

	@Override
	public void interactWith(Cleaner user)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
