package zyx.server.world.humanoids.npc.behavior.cleaner;

import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.npc.Cleaner;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.interactable.common.CommonTable;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.floor.Floor;
import zyx.server.world.interactable.floor.FloorItem;

public class CleanerIdleBehavior extends BaseNpcBehavior<Cleaner, CleanerBehaviorType, Object>
{

	private static final int MAX_GOOF_TIME = 100;

	private final DinnerTable[] dinnerTables;
	private final FoodTable[] foodTables;
	private final Floor floor;

	private int goofTimer;

	public CleanerIdleBehavior(Cleaner npc)
	{
		super(npc, CleanerBehaviorType.IDLE);

		RoomItems room = RoomItems.instance;

		dinnerTables = room.dinnerTables;
		foodTables = room.foodTables;
		floor = room.floor;

		goofTimer = (int) (1000 + (MAX_GOOF_TIME * Math.random()));
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		goofTimer -= elapsedTime;

		if (goofTimer <= 0)
		{
			goofTimer = (int) (1000 + (MAX_GOOF_TIME * Math.random()));

			CommonTable table = findTable();

			if (table != null)
			{
				System.out.println("Found plates on " + table);
				npc.requestBehavior(CleanerBehaviorType.GETTING_DIRTY_PLATE_TABLE, table);
			}
			else if (floor.canBeCleaned())
			{
				System.out.println("Found plates on the floor");
				FloorItem floorItem = floor.getCleanableItem();
				npc.requestBehavior(CleanerBehaviorType.GETTING_DIRTY_PLATE_FLOOR, floorItem);
				
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
