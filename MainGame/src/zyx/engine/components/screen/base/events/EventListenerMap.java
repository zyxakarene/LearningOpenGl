package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;
import zyx.utils.interfaces.IDisposeable;

public class EventListenerMap implements IEventListener, IDisposeable
{

	private ArrayList<AbstractDispatcher> dispatchers;

	public EventListenerMap()
	{
		dispatchers = new ArrayList<>();
		dispatchers.add(new TouchDispatcher());
	}

	@Override
	public void addListener(IDisplayObjectEventListener listener)
	{
		for (AbstractDispatcher dispatcher : dispatchers)
		{
			boolean isMatchingDispatcher = dispatcher.getListenerClass().isInstance(listener);
			if (isMatchingDispatcher)
			{
				dispatcher.add(listener);
			}
		}
	}

	@Override
	public void removeListener(IDisplayObjectEventListener listener)
	{
		for (AbstractDispatcher dispatcher : dispatchers)
		{
			boolean isMatchingDispatcher = dispatcher.getListenerClass().isInstance(listener);
			if (isMatchingDispatcher)
			{
				dispatcher.remove(listener);
			}
		}
	}

	@Override
	public void dispatchEvent(DisplayObjectEvent event)
	{
		for (AbstractDispatcher dispatcher : dispatchers)
		{
			boolean isMatchingDispatcher = dispatcher.getEventClass().isInstance(event);
			if (isMatchingDispatcher)
			{
				dispatcher.dispatch(event);
			}
		}
	}

	@Override
	public void dispose()
	{
		if (dispatchers != null)
		{
			for (AbstractDispatcher dispatcher : dispatchers)
			{
				dispatcher.dispose();
			}
			
			dispatchers.clear();
			dispatchers = null;
		}
	}
}
