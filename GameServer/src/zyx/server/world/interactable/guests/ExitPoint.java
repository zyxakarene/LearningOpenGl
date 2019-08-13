package zyx.server.world.interactable.guests;

import zyx.server.world.humanoids.npc.Guest;

public class ExitPoint extends GuestItem
{

	@Override
	public void interactWith(Guest user)
	{
		System.out.println(user + " left the game!");
	}

}
