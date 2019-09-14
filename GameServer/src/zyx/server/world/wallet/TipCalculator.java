package zyx.server.world.wallet;

import zyx.server.world.humanoids.npc.Guest;

public class TipCalculator
{

	public static int calculateTip(Guest guest)
	{
		int tip = guest.baseTip;
		
		if(!guest.gotRightDish)
		{
			tip = (int) (tip * 0.5f);
		}
		
		float waitRatio = 1 - (guest.waitTime / guest.maxWaitTime);
		
		return (int) (tip * waitRatio);
	}

}
