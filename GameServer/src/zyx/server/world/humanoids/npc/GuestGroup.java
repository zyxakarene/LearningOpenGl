package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.interactable.common.DinnerTable;

public class GuestGroup
{
	public Guest[] groupMembers;
	public DinnerTable table;
	public BillItem bill;

	public GuestGroup(int size)
	{
		groupMembers = new Guest[size];
		
		for (int i = 0; i < size; i++)
		{
			Guest guest = NpcFactory.createGuest();
			guest.group = this;
			guest.isLeader = (i == 0);
			
			groupMembers[i] = guest;
		}
	}
	
}
