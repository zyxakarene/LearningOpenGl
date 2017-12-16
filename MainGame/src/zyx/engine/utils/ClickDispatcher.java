package zyx.engine.utils;

import java.util.LinkedList;
import zyx.engine.components.screen.Button;
import zyx.engine.utils.callbacks.CustomCallback;

public class ClickDispatcher
{
	private static final ClickDispatcher instance = new ClickDispatcher();
	
	private LinkedList<CustomCallback<Button>> callbacks;
	private LinkedList<Button> buttons;
	
	private ClickDispatcher()
	{
		callbacks = new LinkedList<>();
		buttons = new LinkedList<>();
	}
	
	public static ClickDispatcher getInstance()
	{
		return instance;
	}

	public void dispatchEvents()
	{
		CustomCallback<Button> callback;
		Button button;
		while (callbacks.isEmpty() == false)
		{			
			callback = callbacks.removeFirst();
			button = buttons.removeFirst();
			
			callback.dispatch(button);
		}
	}
	
	public void addClick(CustomCallback<Button> callback, Button button)
	{
		callbacks.add(callback);
		buttons.add(button);
	}
}
