package zyx.server.world.interactable.guests;

import zyx.server.world.humanoids.npc.Guest;

public class GuestChair extends GuestItem
{
	private Guest currentGuest;
	
	public GuestChair()
	{
	}

	@Override
	public void interactWith(Guest guest)
	{
		currentGuest = guest;
	}
}
