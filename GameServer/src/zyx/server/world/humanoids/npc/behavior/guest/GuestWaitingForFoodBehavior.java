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
	}
}
