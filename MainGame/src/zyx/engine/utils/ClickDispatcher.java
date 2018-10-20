package zyx.engine.utils;

import java.util.LinkedList;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.CustomCallback;

public class ClickDispatcher
{
	private static final ClickDispatcher instance = new ClickDispatcher();
	
	private LinkedList<CustomCallback<InteractableContainer>> callbacks;
	private LinkedList<InteractableContainer> buttons;
	
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
		CustomCallback<InteractableContainer> callback;
		InteractableContainer button;
		while (callbacks.isEmpty() == false)
		{			
			callback = callbacks.removeFirst();
			button = buttons.removeFirst();
			
			callback.dispatch(button);
		}
	}
	
	public void addClick(CustomCallback<InteractableContainer> callback, InteractableContainer button)
	{
		callbacks.add(callback);
		buttons.add(button);
	}
}
