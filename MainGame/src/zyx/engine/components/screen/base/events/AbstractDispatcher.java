package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;
import zyx.utils.interfaces.IDisposeable;

abstract class AbstractDispatcher<TEvent extends DisplayObjectEvent, TListeners extends IDisplayObjectEventListener> implements IDisposeable
{
	protected ArrayList<TListeners> listeners;

	public AbstractDispatcher()
	{
		listeners = new ArrayList<>();
	}
	
	protected void add(TListeners listener)
	{
		listeners.add(listener);
	}
	
	protected void remove(TListeners listener)
	{
		listeners.remove(listener);
	}
	
	abstract void dispatch(TEvent event);
	
	abstract Class<TEvent> getEventClass();
	abstract Class<TListeners> getListenerClass();

	@Override
	public void dispose()
	{
		if (listeners != null)
		{
			listeners.clear();
			listeners = null;
		}
	}
	
}
