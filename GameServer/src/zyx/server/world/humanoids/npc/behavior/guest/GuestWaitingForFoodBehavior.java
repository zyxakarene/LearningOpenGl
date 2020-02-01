package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;

public class GuestWaitingForFoodBehavior extends GuestBehavior<Object>
{

	public GuestWaitingForFoodBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.WAITING_FOR_FOOD);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (npc.hasEaten)
		{
			System.out.println(npc + " has been given their food. Was it correct? " + npc.gotRightDish);
			npc.requestBehavior(GuestBehaviorType.EATING);
		}
		else if (npc.group.table.hasGottenBill)
		{
			//Got a bill too early, refuse to eat!
			npc.hasEaten = true;
			System.out.println(npc.group.table + " was given the bill too early!!");
			npc.requestBehavior(GuestBehaviorType.WAITING_FOR_BILL);
		}
	}
}
