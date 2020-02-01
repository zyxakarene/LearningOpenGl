package zyx.server.world.interactable.guests;

import zyx.game.vo.FurnitureType;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.BaseInteractableItem;

public abstract class GuestItem extends BaseInteractableItem<Guest>
{

	public GuestItem(FurnitureType type)
	{
		super(type);
	}
	
}
