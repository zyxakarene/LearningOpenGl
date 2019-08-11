package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;

abstract class GuestBehavior<P> extends BaseNpcBehavior<Guest, GuestBehaviorType, P>
{

	GuestBehavior(Guest npc, GuestBehaviorType type)
	{
		super(npc, type);
	}
}
