package zyx.engine.utils;

import java.util.ArrayList;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.GameConstants;
import zyx.utils.math.Vector2Int;

public class ScreenSize
{
	public static int width = GameConstants.WINDOW_WIDTH;
	public static int height = GameConstants.WINDOW_HEIGHT;
	
	private static ArrayList<ICallback<Vector2Int>> listeners = new ArrayList<>();
	
	public static void changeScreenSize(int width, int height)
	{
		ScreenSize.width = width;
		ScreenSize.height = height;
		
		Vector2Int size = new Vector2Int(ScreenSize.width, ScreenSize.height);
		
		for (ICallback<Vector2Int> listener : listeners)
		{
			listener.onCallback(size);
		}
	}
	
	public static void addListener(ICallback<Vector2Int> listener)
	{
		if (listeners.contains(listener) == false)
		{
			listeners.add(listener);
		}
	}
	
	public static void removeListener(ICallback<Vector2Int> listener)
	{
		listeners.remove(listener);
	}
}
