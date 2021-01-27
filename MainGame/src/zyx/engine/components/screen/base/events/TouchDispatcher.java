package zyx.engine.components.screen.base.events;

import zyx.engine.components.screen.base.events.types.touch.ITouchedListener;
import zyx.engine.components.screen.base.events.types.touch.TouchEvent;

class TouchDispatcher extends AbstractDispatcher<TouchEvent, ITouchedListener>
{

	TouchDispatcher()
	{
	}

	@Override
	void dispatch(TouchEvent event)
	{
		switch (event.type)
		{
			case Enter:
			{
				for (ITouchedListener listener : listeners)
				{
					listener.mouseEnter(event);
				}
				break;
			}
			case Leave:
			{
				for (ITouchedListener listener : listeners)
				{
					listener.mouseLeave(event);
				}
				break;
			}
			case Down:
			{
				for (ITouchedListener listener : listeners)
				{
					listener.mouseDown(event);
				}
				break;
			}
			case Up:
			{
				for (ITouchedListener listener : listeners)
				{
					listener.mouseUp(event);
				}
				break;
			}
			case Click:
			{
				for (ITouchedListener listener : listeners)
				{
					listener.mouseClick(event);
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
	Class<ITouchedListener> getListenerClass()
	{
		return ITouchedListener.class;
	}
}
