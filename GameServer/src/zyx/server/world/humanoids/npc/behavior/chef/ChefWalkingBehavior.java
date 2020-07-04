package zyx.server.world.humanoids.npc.behavior.chef;

import zyx.server.world.humanoids.npc.Chef;
import zyx.server.world.humanoids.npc.behavior.BaseNpcWalkingBehavior;
import zyx.server.world.interactable.BaseInteractableItem;


public abstract class ChefWalkingBehavior<P extends BaseInteractableItem<Chef>> extends BaseNpcWalkingBehavior<Chef, ChefBehaviorType, P>
{

	public ChefWalkingBehavior(Chef npc, ChefBehaviorType type)
	{
		super(npc, type);
	}
}
