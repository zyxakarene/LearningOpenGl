package zyx.server.world.humanoids.npc;

public class GuestGroup
{
	public Guest[] groupMembers;

	public GuestGroup(int size)
	{
		groupMembers = new Guest[size];
		
		for (int i = 0; i < size; i++)
		{
			Guest guest = NpcFactory.createGuest();
			guest.group = this;
			
			groupMembers[i] = guest;
		}
	}
	
}
