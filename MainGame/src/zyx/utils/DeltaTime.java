package zyx.utils;

import org.lwjgl.input.Keyboard;
import zyx.game.controls.KeyboardControl;

public class DeltaTime
{

	private static final float MS_PER_FRAME = GameConstants.MS_PER_FRAME;

	private static long timestampCounter = System.currentTimeMillis() - GameConstants.MS_PER_FRAME;

	private static long lastTimestamp = 0;
	private static int lastDeltaTime = 1;
	private static float lastDeltaVariant = 1;

	public static void update()
	{
//		if (KeyboardControl.wasKeyPressed(Keyboard.KEY_2))
//		{
//			long newTimestamp = lastTimestamp + 16;
//
//			lastDeltaTime = (int) (newTimestamp - lastTimestamp);
//			lastTimestamp = newTimestamp;
//
//			lastDeltaVariant = MS_PER_FRAME / lastDeltaTime;
//		}
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
