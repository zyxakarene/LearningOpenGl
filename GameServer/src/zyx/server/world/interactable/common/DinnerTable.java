package zyx.server.world.interactable.common;

import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.guests.GuestChair;

public class DinnerTable extends CommonTable<Guest>
{
	private static final int MAX_GUEST_TABLE_ITEM_COUNT = 5;
	
	private GuestChair[] chairs;
	
	public DinnerTable(GuestChair[] connectedChairs)
	{
		super(MAX_GUEST_TABLE_ITEM_COUNT);
		chairs = connectedChairs;
	}

}
