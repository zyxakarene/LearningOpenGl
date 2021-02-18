package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.focus.*;

class FocusDispatcher extends AbstractDispatcher<FocusEvent, IFocusListener>
{

	FocusDispatcher()
	{
	}

	@Override
	protected void addRegistrations()
	{
		registerEventInterface(IFocusChangedListener.class, FocusEventType.Changed);
		registerEventInterface(IFocusKeyListener.class, FocusEventType.KeyTyped);
	}

	@Override
	protected void dispatchEvent(FocusEvent event, ArrayList<IFocusListener> listeners)
	{
		switch (event.type)
		{
			case Changed:
			{
				for (IFocusListener listener : listeners)
				{
					((IFocusChangedListener) listener).focusChanged(event);
				}
				break;
			}
			case KeyTyped:
			{
				for (IFocusListener listener : listeners)
				{
					((IFocusKeyListener) listener).focusKeyPressed(event);
				}
				break;
			}
		}
	}
	
	@Override
	Class<FocusEvent> getEventClass()
	{
		return FocusEvent.class;
	}

	@Override
	Class<IFocusListener> getListenerClass()
	{
		return IFocusListener.class;
	}
}
