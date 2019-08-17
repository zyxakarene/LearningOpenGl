package zyx.server.world.interactable.guests;

import zyx.server.controller.services.NpcService;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.NpcManager;

public class ExitPoint extends GuestItem
{

	@Override
	public void interactWith(Guest user)
	{
		NpcManager.getInstance().removeEntity(user);
		NpcService.removeGuest(user);
	}

}
