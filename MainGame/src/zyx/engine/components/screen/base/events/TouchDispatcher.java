package zyx.engine.components.screen.base.events;

import zyx.engine.components.screen.base.events.types.mouse.IMouseUpListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseDownListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseClickedListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseExitedListener;
import zyx.engine.components.screen.base.events.types.mouse.IMouseDraggedListener;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.engine.components.screen.base.events.types.mouse.IMouseEnteredListener;
import zyx.engine.components.screen.base.events.types.mouse.MouseEventType;
import java.util.ArrayList;

class TouchDispatcher extends AbstractDispatcher<MouseEvent, IMouseListener>
{

	TouchDispatcher()
	{
	}

	@Override
	protected void addRegistrations()
	{
		registerEventInterface(IMouseClickedListener.class, MouseEventType.Click);
		registerEventInterface(IMouseDownListener.class, MouseEventType.Down);
		registerEventInterface(IMouseDraggedListener.class, MouseEventType.Drag);
		registerEventInterface(IMouseEnteredListener.class, MouseEventType.Enter);
		registerEventInterface(IMouseExitedListener.class, MouseEventType.Exit);
		registerEventInterface(IMouseUpListener.class, MouseEventType.Up);
	}
	
	@Override
	protected void dispatchEvent(MouseEvent event, ArrayList<IMouseListener> listeners)
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
	Class<MouseEvent> getEventClass()
	{
		return MouseEvent.class;
	}

	@Override
	Class<IMouseListener> getListenerClass()
	{
		return IMouseListener.class;
	}
}
