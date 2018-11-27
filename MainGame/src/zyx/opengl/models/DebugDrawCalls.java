package zyx.opengl.models;

public class DebugDrawCalls
{
	private static final Object LOCK = new Object();
	
	private static int limitUi = -1;
	private static int limitWorld = -1;
	private static int currentDrawWorld;
	private static int currentDrawUi;
	
	public static void setUiDrawLimit(int limit)
	{
		synchronized (LOCK)
		{
			limitUi = limit;
		}
	}
	
	public static void setWorldDrawLimit(int limit)
	{
		synchronized (LOCK)
		{
			limitWorld = limit;
		}
	}
	
	public static void reset()
	{
		synchronized (LOCK)
		{
			currentDrawWorld = 0;
			currentDrawUi = 0;
		}
	}
	
	public static boolean canDrawWorld()
	{
		synchronized (LOCK)
		{
			if (limitWorld != -1 && currentDrawWorld + 1 > limitWorld)
			{
				return false;
			}
			
			currentDrawWorld++;
			return true;
		}
	}
	
	public static boolean canDrawUi()
	{
		synchronized (LOCK)
		{
			if (limitUi != -1 && currentDrawUi + 1 > limitUi)
			{
				return false;
			}
			
			currentDrawUi++;
			return true;
		}
	}

	public static int getCurrentDrawWorld()
	{
		synchronized (LOCK)
		{
			return currentDrawWorld;
		}
	}

	public static int getCurrentDrawUi()
	{
		synchronized (LOCK)
		{
			return currentDrawUi;
		}
	}
}
