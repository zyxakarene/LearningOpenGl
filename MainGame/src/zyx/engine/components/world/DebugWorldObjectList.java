package zyx.engine.components.world;

public class DebugWorldObjectList
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
	
	public static WorldObjectNode getActiveWorldObjects()
	{
		synchronized(LOCK)
		{
			update = false;
			
			WorldObject base = World3D.instance;
			
			WorldObjectNode result = new WorldObjectNode(base);
			
			return result;
		}
	}
}
