package zyx.server.world.humanoids.npc.behavior.chef.finding;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.common.FoodTable;

public class ChefFindingTableBehavior extends BaseNpcBehavior<Chef, ChefBehaviorType, Object>
{
	private FoodTable[] tables;
	
	public ChefFindingTableBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.FINDING_TABLE);
		
		tables = items.foodTables;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (FoodTable table : tables)
		{
			if (table.canCarryMoreItems())
			{
				npc.requestBehavior(ChefBehaviorType.SERVING_FOOD_TABLE, table);
			}
		}
	}
}
