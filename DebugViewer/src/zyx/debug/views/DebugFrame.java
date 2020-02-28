package zyx.debug.views;

import zyx.debug.DebugController;

public class DebugFrame
{
	public static void onFrame()
	{
		synchronized(DebugController.SHARED_LOCK)
		{
			DebugController.SHARED_LOCK.notifyAll();
		}
	}
}
