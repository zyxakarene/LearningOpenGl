package zyx.debug.link;

import zyx.engine.components.world.World3D;
import zyx.engine.components.world.WorldObject;
import zyx.engine.components.world.WorldObjectNode;

public class DebugWorldObjectLink
{
	private final Object LOCK = new Object();
	private boolean update = false;

	DebugWorldObjectLink()
	{
	}
	
	public void updateList()
	{
		synchronized (LOCK)
		{
			update = true;
		}
	}

	public boolean hasUpdate()
	{
		synchronized (LOCK)
		{
			return update;
		}
	}
	
	public WorldObjectNode getActiveWorldObjects()
	{
		synchronized(LOCK)
		{
			update = false;
			
			WorldObject base = World3D.instance;
			
			return new WorldObjectNode(base);
		}
	}
}
