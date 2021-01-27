package zyx.engine.components.screen.base.events;

import java.util.HashMap;
import java.util.Map;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;
import zyx.utils.interfaces.IDisposeable;

public class EventListenerMap implements IEventListener, IDisposeable
{

	private HashMap<Class, AbstractDispatcher> dispatchers;

	public EventListenerMap()
	{
		dispatchers = new HashMap<>();
		addDispatcher(new TouchDispatcher());
	}

	private void addDispatcher(AbstractDispatcher dispatcher)
	{
		dispatchers.put(dispatcher.getEventClass(), dispatcher);
		dispatchers.put(dispatcher.getListenerClass(), dispatcher);
	}

	@Override
	public void addListener(IDisplayObjectEventListener listener)
	{
		Class key = listener.getTypeClass();
		AbstractDispatcher dispatcher = dispatchers.get(key);

		if (dispatcher != null )
		{
			dispatcher.add(listener);
		}
	}

	@Override
	public void removeListener(IDisplayObjectEventListener listener)
	{
		Class key = listener.getTypeClass();
		AbstractDispatcher dispatcher = dispatchers.get(key);

		if (dispatcher != null)
		{
			dispatcher.remove(listener);
		}
	}

	@Override
	public void dispatchEvent(DisplayObjectEvent event)
	{
		Class key = event.getClass();
		AbstractDispatcher dispatcher = dispatchers.get(key);

		if (dispatcher != null)
		{
			dispatcher.dispatch(event);
		}
	}

	@Override
	public void dispose()
	{
		if (dispatchers != null)
		{
			for (Map.Entry<Class, AbstractDispatcher> entry : dispatchers.entrySet())
			{
				AbstractDispatcher dispatcher = entry.getValue();
				dispatcher.dispose();
			}
			
			dispatchers.clear();
			dispatchers = null;
		}
	}
}
