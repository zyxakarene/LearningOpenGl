package zyx.engine.focus;

import java.util.ArrayList;
import java.util.List;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.touch.ITouched;
import zyx.engine.touch.MouseTouchManager;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.PressedKey;
import zyx.utils.interfaces.IUpdateable;

public class FocusManager implements IUpdateable, ITouched
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
		
		MouseTouchManager.getInstance().unregisterTouch((DisplayObject) focusable, this);
		
		focusables.remove(focusable);
	}

	public void add(IFocusable focusable)
	{
		if (focusables.contains(focusable) == false)
		{
			focusables.add(focusable);
			
			MouseTouchManager.getInstance().registerTouch((DisplayObject) focusable, this);
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

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
		if (currentFocus != null)
		{
			currentFocus.onUnFocused();
			currentFocus = null;
		}
		
		if (data.target instanceof IFocusable)
		{
			currentFocus = (IFocusable) data.target;
		}
		
		if (currentFocus != null)
		{
			currentFocus.onFocused();
		}
	}
}
