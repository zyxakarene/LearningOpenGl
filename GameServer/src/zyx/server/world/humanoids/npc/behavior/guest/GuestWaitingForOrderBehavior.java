package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;

public class GuestWaitingForOrderBehavior extends GuestBehavior<Object>
{

	public GuestWaitingForOrderBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WAITING_TO_ORDER);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (npc.group.table.hasGottenBill)
		{
			//Got a bill too early, refuse to eat!
			npc.hasEaten = true;
			System.out.println(npc.group.table + " was given the bill too early!!");
			npc.requestBehavior(GuestBehaviorType.WAITING_FOR_BILL);
		}
	}
}
