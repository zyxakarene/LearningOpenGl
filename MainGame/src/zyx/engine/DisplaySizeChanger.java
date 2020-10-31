package zyx.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.cheats.Print;
import zyx.utils.math.Vector2Int;

public class DisplaySizeChanger implements ICallback<Vector2Int>
{

	public DisplaySizeChanger()
	{
		ScreenSize.addListener(this);
	}

	@Override
	public void onCallback(Vector2Int data)
	{
//		try
//		{
//			Display.setDisplayMode(new DisplayMode(data.x, data.y));
//		}
//		catch (LWJGLException ex)
//		{
//			String msg = "[Error] Could not change LWGL Display size.";
//			Print.err(msg, ex.getMessage());
//			
//			System.exit(-1);
//		}
	}
}
