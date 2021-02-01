package zyx.engine.focus;

import java.util.ArrayList;
import java.util.List;
import zyx.engine.components.animations.IFocusable;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.PressedKey;
import zyx.utils.interfaces.IUpdateable;

public class FocusManager implements IUpdateable
{

	private static final FocusManager INSTANCE = new FocusManager();
	
	private ArrayList<IFocusable> focusables;
	private IFocusable currentFocus;
	
	private FocusManager()
	{
		focusables = new ArrayList<>();
	}

	public static FocusManager getInstance()
	{
		return INSTANCE;
	}

	public void remove(IFocusable focusable)
	{
		if (focusable == currentFocus)
		{
			currentFocus.onUnFocused();
			currentFocus = null;
		}
				
		focusables.remove(focusable);
	}

	public void add(IFocusable focusable)
	{
		if (focusables.contains(focusable) == false)
		{
			focusables.add(focusable);
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (currentFocus != null)
		{
			List<PressedKey> charList = KeyboardData.data.pressedCharacters;

			int len = charList.size();
			for (int i = 0; i < len; i++)
			{
				PressedKey key = charList.get(i);
				currentFocus.onKeyPressed(key.character);
			}
		}
	}
}
