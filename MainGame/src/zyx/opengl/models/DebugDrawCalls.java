package zyx.opengl.models;

public class DebugDrawCalls
{
	private static final Object LOCK = new Object();
	
	private static int limitUi = -1;
	private static int limitWorld = 1;
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
			currentDrawWorld++;
			return limitWorld == -1 || currentDrawWorld <= limitWorld;
		}
	}
	
	public static boolean canDrawUi()
	{
		synchronized (LOCK)
		{
			currentDrawUi++;
			return limitUi == -1 || currentDrawUi <= limitUi;
		}
	}
}
