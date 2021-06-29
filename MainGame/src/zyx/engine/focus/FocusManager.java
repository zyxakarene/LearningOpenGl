package zyx.engine.focus;

import java.util.ArrayList;
import java.util.List;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.events.types.mouse.IMouseClickedListener;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.game.controls.input.KeyboardData;
import zyx.game.controls.input.PressedKey;
import zyx.utils.interfaces.IUpdateable;

public class FocusManager implements IUpdateable
{

	private static final FocusManager INSTANCE = new FocusManager();
	
	private ArrayList<IFocusable> focusables;
	private ArrayList<IMouseClickedListener> clickListeners;
	private IFocusable currentFocus;
	
	private FocusManager()
	{
		focusables = new ArrayList<>();
		clickListeners = new ArrayList<>();
	}

	public static FocusManager getInstance()
	{
		return INSTANCE;
	}

	public void remove(IFocusable focusable)
	{
		if (focusable == currentFocus)
		{
			currentFocus = null;
		}
				
		focusables.remove(focusable);
	}

	public void add(IFocusable focusable)
	{
		if (focusables.contains(focusable) == false)
		{
			IMouseClickedListener listener = this::onFocusObjectClicked;
			focusable.addListener(listener);
			
			focusables.add(focusable);
			clickListeners.add(listener);
		}
	}
	
	private void onFocusObjectClicked(MouseEvent event)
	{
		DisplayObject target = event.target;
		if (target instanceof IFocusable)
		{
			if (currentFocus != null)
			{
				currentFocus.onFocusChanged(false);
			}
			
			currentFocus = (IFocusable) target;
			currentFocus.onFocusChanged(true);
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
