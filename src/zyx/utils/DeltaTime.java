package zyx.utils;

public class DeltaTime
{
	private static final float MS_PER_FRAME = GameConstants.MS_PER_FRAME;
	
	private static long lastTimestamp = System.currentTimeMillis() - GameConstants.MS_PER_FRAME;
	private static int lastDeltaTime = 1;
	private static float lastDeltaVariant = 1;

	public static void update()
	{
		long newTimestamp = System.currentTimeMillis();
		
		lastDeltaTime = (int) (newTimestamp - lastTimestamp);
		lastTimestamp = newTimestamp;
		
		lastDeltaVariant = MS_PER_FRAME / lastDeltaTime;
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
