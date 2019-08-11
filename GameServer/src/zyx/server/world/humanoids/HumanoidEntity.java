package zyx.server.world.humanoids;

import zyx.game.vo.Gender;
import zyx.server.world.entity.WorldEntity;
import zyx.server.world.humanoids.handheld.HandheldItem;

public class HumanoidEntity extends WorldEntity
{
	public final String name;
	public final Gender gender;
	
	protected HandheldItem heldItem;
	
	public HumanoidEntity(String name, Gender gender)
	{
		this.name = name;
		this.gender = gender;
	}

	public HandheldItem heldItem()
	{
		return heldItem;
	}
	
	public boolean isHoldingAnything()
	{
		return heldItem != null;
	}
	
	public boolean canHoldItem()
	{
		return heldItem == null;
	}
	
	public HandheldItem removeItem()
	{
		HandheldItem item = heldItem;
		
		heldItem = null;
		return item;
	}

	public void pickupItem(HandheldItem item)
	{
		heldItem = item;
	}
	
	public boolean wantsToHold(HandheldItem item)
	{
		return true;
	}
}
