package zyx.server.world.humanoids.npc.behavior;

import zyx.server.utils.IUpdateable;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.npc.BaseNpc;

public abstract class BaseNpcBehavior<NPC extends BaseNpc, T extends Enum, P> implements IUpdateable
{
	private static final String TOSTRING_TEMPLATE = "Behavior(%s)";
	
	public final T type;
	
	protected final NPC npc;
	protected P params;
	
	protected RoomItems items;

	public BaseNpcBehavior(NPC npc, T type)
	{
		this.npc = npc;
		this.type = type;
		items = RoomItems.instance;
	}
	
	public void setParams(P obj)
	{
		params = obj;
	}
	
	public final void enter()
	{
		System.out.println(npc + " has entered the behavior: " + this);
		onEnter();
	}
	
	protected void onEnter()
	{
	}
	
	public void exit()
	{
		
	}

	@Override
	public String toString()
	{
		String clazz = getClass().getSimpleName();
		return String.format(TOSTRING_TEMPLATE, clazz);
	}

	
	
	
}
