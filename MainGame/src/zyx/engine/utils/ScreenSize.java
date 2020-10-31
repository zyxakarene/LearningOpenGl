package zyx.engine.utils;

import java.util.ArrayList;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.GameConstants;
import zyx.utils.math.Vector2Int;

public class ScreenSize
{
	public static int windowWidth = GameConstants.WINDOW_WIDTH;
	public static int windowHeight = GameConstants.WINDOW_HEIGHT;
	
	public static int gameWidth = GameConstants.DEFAULT_GAME_WIDTH;
	public static int gameHeight = GameConstants.DEFAULT_GAME_HEIGHT;
	
	public static int gamePosX = (GameConstants.WINDOW_WIDTH / 2) - (GameConstants.DEFAULT_GAME_WIDTH / 2);
	public static int gamePosY = 0;
	
	private static ArrayList<ICallback<Vector2Int>> listeners = new ArrayList<>();
	
	public static void changeScreenSize(int width, int height)
	{
		ScreenSize.windowWidth = width;
		ScreenSize.windowHeight = height;
		
		ScreenSize.gameWidth = (int) (width * 0.5f);
		ScreenSize.gameHeight = (int) (height * 0.75f);
		
		ScreenSize.gamePosX = (int) (ScreenSize.windowWidth / 2) - (ScreenSize.gameWidth / 2);
		ScreenSize.gamePosY = 0;
		
		Vector2Int size = new Vector2Int(ScreenSize.windowWidth, ScreenSize.windowHeight);
		
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
