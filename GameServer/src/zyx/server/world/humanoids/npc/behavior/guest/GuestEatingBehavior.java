package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.humanoids.npc.Guest;

public class GuestEatingBehavior extends GuestBehavior<Object>
{
	private int eatingTime;
	
	public GuestEatingBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.EATING);
		
		eatingTime = 100;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (eatingTime > 0)
		{
			eatingTime -= elapsedTime;
			
			if (eatingTime <= 0)
			{
				System.out.println(npc + " has finished eating");
				npc.stopEating();
				npc.requestBehavior(GuestBehaviorType.WAITING_FOR_BILL);
			}
		}
	}
}
