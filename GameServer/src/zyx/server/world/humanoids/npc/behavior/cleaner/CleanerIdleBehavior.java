package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.interactable.common.CommonTable;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;

public class CleanerIdleBehavior extends BaseNpcBehavior<Cleaner, CleanerBehaviorType, Object>
{
	private static final int MAX_GOOF_TIME = 100;
	
	private final DinnerTable[] dinnerTables;
	private final FoodTable[] foodTables;
	
	private int goofTimer;

	public CleanerIdleBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.IDLE);
		
		dinnerTables = RoomItems.instance.dinnerTables;
		foodTables = RoomItems.instance.foodTables;
		
		goofTimer = (int) (1000 + (MAX_GOOF_TIME * Math.random()));
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		goofTimer -= elapsedTime;
		
		if (goofTimer <= 0)
		{
			System.out.println(npc + " is checking for dirty plates");
			goofTimer = (int) (1000 + (MAX_GOOF_TIME * Math.random()));
			
			CommonTable table = findTable();
			
			if (table != null)
			{
				System.out.println("Found plates on " + table);
				npc.requestBehavior(CleanerBehaviorType.GETTING_DIRTY_PLATE, table);
			}
		}
	}

	private CommonTable findTable()
	{
		for (DinnerTable table : dinnerTables)
		{
			if (table.canBeCleaned())
			{
				return table;
			}
		}
		
		for (FoodTable table : foodTables)
		{
			if (table.canBeCleaned())
			{
				return table;
			}
		}
		
		return null;
	}
}
