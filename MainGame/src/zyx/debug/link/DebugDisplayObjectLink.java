package zyx.debug.link;

import zyx.engine.components.screen.base.DisplayObjectNode;
import zyx.engine.components.screen.base.Stage;

public class DebugDisplayObjectLink
{
	private final Object LOCK = new Object();
	private boolean update = false;

	DebugDisplayObjectLink()
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
	
	public DisplayObjectNode getActiveDisplayObjects()
	{
		synchronized(LOCK)
		{
			update = false;
			
			Stage base = Stage.getInstance();
			
			return new DisplayObjectNode(base);
		}
	}
}
