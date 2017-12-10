package zyx.utils;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPSCounter
{

	private static final String FPS = "FPS: ";
    private static long lastFPS = getTime();
    private static long fps = 0;
    static
    {
        Display.setTitle("FPS: N/A");
    }
    
    private static long getTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static void updateFPS()
    {
        if (getTime() - lastFPS > 1000)
        {
            Display.setTitle(FPS + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
}
