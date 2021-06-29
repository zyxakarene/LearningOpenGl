package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.size.ISizeChangedListener;
import zyx.engine.components.screen.base.events.types.size.ISizeListener;
import zyx.engine.components.screen.base.events.types.size.SizeEvent;
import zyx.engine.components.screen.base.events.types.size.SizeEventType;

class SizeDispatcher extends AbstractDispatcher<SizeEvent, ISizeListener>
{

	SizeDispatcher()
	{
	}

	@Override
	protected void addRegistrations()
	{
		registerEventInterface(ISizeListener.class, SizeEventType.Changed);
	}

	@Override
	protected void dispatchEvent(SizeEvent event, ArrayList<ISizeListener> listeners)
	{
		switch (event.type)
		{
			case Changed:
			{
				for (ISizeListener listener : listeners)
				{
					((ISizeChangedListener) listener).sizeChanged(event);
				}
				break;
			}
		}
	}
	
	@Override
	Class<SizeEvent> getEventClass()
	{
		return SizeEvent.class;
	}

	@Override
	Class<ISizeListener> getListenerClass()
	{
		return ISizeListener.class;
	}
}
