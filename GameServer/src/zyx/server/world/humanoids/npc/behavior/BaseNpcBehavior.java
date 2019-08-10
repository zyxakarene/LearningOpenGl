package zyx.server.world.humanoids.npc.behavior;

import zyx.server.utils.IUpdateable;
import zyx.server.world.humanoids.npc.BaseNpc;

public abstract class BaseNpcBehavior<T extends Enum> implements IUpdateable
{
	public final T type;
	
	protected final BaseNpc npc;

	public BaseNpcBehavior(BaseNpc npc, T type)
	{
		this.npc = npc;
		this.type = type;
	}
}
