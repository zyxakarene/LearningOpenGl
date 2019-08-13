package zyx.server.world.humanoids.npc.behavior.guest;

import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.npc.Guest;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.guests.Chair;

public class GuestIdleBehavior extends GuestBehavior<Object>
{

	private final DinnerTable[] allTables;

	public GuestIdleBehavior(Guest npc)
	{
		super(npc, GuestBehaviorType.IDLE);
		
		RoomItems items = RoomItems.instance;
		allTables = items.dinnerTables;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (npc.isLeader && npc.group.table == null)
		{
			System.out.println(npc + " is leader, looking for a table");
			int groupSize = npc.group.groupMembers.length;
			
			for (DinnerTable table : allTables)
			{
				if (!table.inUse && table.chairCount <= groupSize && table.isEmpty())
				{
					claimTable(table);
					return;
				}
			}
		}
		
		if (npc.group.table != null)
		{
			findSeat(npc.group.table);
		}
	}

	private void claimTable(DinnerTable table)
	{
		System.out.println(npc + " found table " + table + " and claimed it!");
		table.claimOwnership(npc);
		npc.group.table = table;
	}

	private void findSeat(DinnerTable table)
	{
		for (Chair chair : table.chairs)
		{
			if (!chair.inUse)
			{
				System.out.println(npc + " found chair " + chair + " and claimed it!");
				chair.claimOwnership(npc);
				npc.requestBehavior(GuestBehaviorType.WALKING_TO_CHAIR, chair);
				return;
			}
		}
		
		System.out.println("GUEST FOUND NO CHAIRS!!!");
		npc.requestBehavior(GuestBehaviorType.WALKING_OUT);
	}
}
