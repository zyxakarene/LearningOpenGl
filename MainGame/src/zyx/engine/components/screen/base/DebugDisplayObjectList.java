package zyx.engine.components.screen.base;

public class DebugDisplayObjectList
{
	private static final Object LOCK = new Object();
	private static boolean update = false;

	static void updateList()
	{
		synchronized (LOCK)
		{
			update = true;
		}
	}

	public static boolean hasUpdate()
	{
		synchronized (LOCK)
		{
			return update;
		}
	}
	
	public static DisplayObjectNode getActiveDisplayObjects()
	{
		synchronized(LOCK)
		{
			update = false;
			
			Stage base = Stage.instance;
			
			return new DisplayObjectNode(base);
		}
	}
}
