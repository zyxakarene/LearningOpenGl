package zyx.opengl.models;

public class DebugDrawCalls
{
	private static final Object LOCK = new Object();
	
	private static int limitUi = -1;
	private static int limitWorld = -1;
	private static int currentDrawWorld;
	private static int currentDrawUi;
	
	private static int highlightedDrawUi = -1;
	private static int highlightedDrawWorld = -1;
	
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

	public static boolean shouldHighlightWorld()
	{
		synchronized (LOCK)
		{
			return currentDrawWorld == highlightedDrawWorld;
		}
	}

	public static boolean shouldHighlightUi()
	{
		synchronized (LOCK)
		{
			return currentDrawUi == highlightedDrawUi;
		}
	}

	public static void setUiHighlight(boolean selected)
	{
		synchronized (LOCK)
		{
			highlightedDrawUi = selected ? limitUi : -1;
		}
	}

	public static void setWorldHighlight(boolean selected)
	{
		synchronized (LOCK)
		{
			highlightedDrawWorld = selected ? limitWorld - 1 : -1;
		}
	}
}
