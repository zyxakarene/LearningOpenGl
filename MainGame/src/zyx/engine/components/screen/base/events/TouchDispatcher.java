package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.touch.*;

class TouchDispatcher extends AbstractDispatcher<TouchEvent, IMouseListener>
{

	TouchDispatcher()
	{
	}

	@Override
	protected void addRegistrations()
	{
		registerEventInterface(IMouseClickedListener.class, TouchEventType.Click);
		registerEventInterface(IMouseDownListener.class, TouchEventType.Down);
		registerEventInterface(IMouseDraggedListener.class, TouchEventType.Drag);
		registerEventInterface(IMouseEnteredListener.class, TouchEventType.Enter);
		registerEventInterface(IMouseExitedListener.class, TouchEventType.Exit);
		registerEventInterface(IMouseUpListener.class, TouchEventType.Up);
	}
	
	@Override
	protected void dispatchEvent(TouchEvent event, ArrayList<IMouseListener> listeners)
	{
		switch (event.type)
		{
			case Enter:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseEnteredListener)listener).mouseEnter(event);
				}
				break;
			}
			case Exit:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseExitedListener)listener).mouseExit(event);
				}
				break;
			}
			case Down:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseDownListener)listener).mouseDown(event);
				}
				break;
			}
			case Up:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseUpListener)listener).mouseUp(event);
				}
				break;
			}
			case Click:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseClickedListener)listener).mouseClick(event);
				}
				break;
			}
			case Drag:
			{
				for (IMouseListener listener : listeners)
				{
					((IMouseDraggedListener)listener).mouseDragged(event);
				}
				break;
			}
		}
	}

	@Override
	Class<TouchEvent> getEventClass()
	{
		return TouchEvent.class;
	}

	@Override
	Class<IMouseListener> getListenerClass()
	{
		return IMouseListener.class;
	}
}
