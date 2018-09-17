package zyx.engine.curser;

import java.util.HashMap;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import zyx.utils.cheats.Print;
import zyx.utils.exceptions.Msg;

public class CursorManager
{

	private static final CursorManager INSTANCE = new CursorManager();

	private HashMap<GameCursor, Cursor> cursorMap;
	
	private GameCursor currentCursor = GameCursor.POINTER;
	private GameCursor oldCursor = null;

	private CursorManager()
	{
		cursorMap = new HashMap<>();
	}

	public static CursorManager getInstance()
	{
		return INSTANCE;
	}

	public void setCursor(GameCursor cursor)
	{
		currentCursor = cursor;
	}

	public void update()
	{
		if (oldCursor == currentCursor)
		{
			return;
		}
		
		try
		{
			oldCursor = currentCursor;
			Cursor cursorToUse = cursorMap.get(oldCursor);
			Mouse.setNativeCursor(cursorToUse);
		}
		catch (LWJGLException ex)
		{
			Msg.error("Could not set cursor", ex);
		}
	}
	
	public void initialize()
	{
		GameCursor[] cursors = GameCursor.values();
		for (GameCursor cursor : cursors)
		{
			new GameCurserLoader().loadCursor(cursor);
		}
	}

	void cursorLoaded(GameCursor gameCursor, Cursor cursor)
	{
		cursorMap.put(gameCursor, cursor);
		
		if (gameCursor == GameCursor.POINTER)
		{
			setCursor(gameCursor);
		}
	}
}
