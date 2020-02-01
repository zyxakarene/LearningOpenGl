package zyx.server.world.humanoids.npc.behavior.chef;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcBehavior;
import zyx.server.world.interactable.chef.Monitor;

public class ChefIdleBehavior extends BaseNpcBehavior<Chef, ChefBehaviorType, Object>
{

	private Monitor orderMonitor;

	public ChefIdleBehavior(Chef npc)
	{
		super(npc, ChefBehaviorType.IDLE);
		orderMonitor = items.orderMonitor();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		boolean hasOrders = orderMonitor.hasOrders();
		
		if (hasOrders)
		{
			npc.requestBehavior(ChefBehaviorType.FINDING_MONITOR);
		}
	}
}
