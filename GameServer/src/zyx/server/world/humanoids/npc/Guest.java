package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.npc.behavior.guest.GuestBehaviorType;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public class Guest extends BaseNpc<GuestBehaviorType>
{

	public Guest(NpcSetup setup)
	{
		super(setup);
	}
}
