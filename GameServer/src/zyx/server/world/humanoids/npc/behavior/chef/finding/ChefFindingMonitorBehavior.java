package zyx.server.world.humanoids.npc.behavior.chef.finding;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.interactable.chef.OrderMonitor;

public class ChefFindingMonitorBehavior extends BaseNpcBehavior<Chef, ChefBehaviorType, Object>
{

	private OrderMonitor monitor;

	public ChefFindingMonitorBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.FINDING_MONITOR);

		monitor = items.orderMonitor();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (monitor.hasOrders())
		{
			if (!monitor.inUse)
			{
				monitor.claimOwnership(npc);
				npc.requestBehavior(ChefBehaviorType.GETTING_RECIPE_MONITOR, monitor);
			}
		}
		else
		{
			npc.requestBehavior(ChefBehaviorType.IDLE);
		}
	}
}
