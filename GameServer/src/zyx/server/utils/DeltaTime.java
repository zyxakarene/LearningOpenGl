package zyx.server.utils;

public class DeltaTime
{

	private static final float MS_PER_FRAME = 16;

	private static long timestampCounter;

	private static long lastTimestamp = 0;
	private static int lastDeltaTime = 1;
	private static float lastDeltaVariant = 1;

	public static void start()
	{
		timestampCounter = System.currentTimeMillis() - ((int) MS_PER_FRAME);
	}
	
	public static void update()
	{
		long newTimestamp = System.currentTimeMillis();

		lastDeltaTime = (int) (newTimestamp - timestampCounter);
		timestampCounter = newTimestamp;

		lastDeltaVariant = MS_PER_FRAME / lastDeltaTime;
		lastTimestamp += lastDeltaTime;
	}

	public static int getElapsedTime()
	{
		return lastDeltaTime;
	}

	public static long getTimestamp()
	{
		return lastTimestamp;
	}

	public static float getDeltaVariant()
	{
		return lastDeltaVariant;
	}
}
