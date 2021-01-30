package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;
import zyx.utils.interfaces.IDisposeable;

abstract class AbstractDispatcher<TEvent extends DisplayObjectEvent, TListeners extends IDisplayObjectEventListener> implements IDisposeable
{
	private ArrayList<ArrayList<TListeners>> listenersList;
	private ArrayList<Class<? extends TListeners>> classList;
	private ArrayList<Enum> enumList;
	
	private int size;

	
	public AbstractDispatcher()
	{
		listenersList = new ArrayList<>();
		classList = new ArrayList<>();
		enumList = new ArrayList<>();
		size = 0;
	}
	
	protected abstract void addRegistrations();
	
	protected final void registerEventInterface(Class<? extends TListeners> listenerClass, Enum e)
	{
		ArrayList<TListeners> listeners = new ArrayList<>();
		
		listenersList.add(listeners);
		classList.add(listenerClass);
		enumList.add(e);
		
		size++;
	}
	
	protected final void add(TListeners listener)
	{
		if (size == 0)
		{
			addRegistrations();
		}
		
		for (int i = 0; i < size; i++)
		{
			Class<? extends TListeners> clazz = classList.get(i);
			
			if (clazz.isInstance(listener))
			{
				ArrayList<TListeners> listeners = listenersList.get(i);
				listeners.add(listener);
			}
		}
	}
	
	protected final void remove(TListeners listener)
	{
		for (int i = 0; i < size; i++)
		{
			Class<? extends TListeners> clazz = classList.get(i);
			
			if (clazz.isInstance(listener))
			{
				ArrayList<TListeners> listeners = listenersList.get(i);
				listeners.remove(listener);
			}
		}
	}
	
	final void dispatch(TEvent event)
	{
		for (int i = 0; i < size; i++)
		{
			Enum e = enumList.get(i);
			
			if (e == event.type)
			{
				ArrayList<TListeners> listeners = listenersList.get(i);
				dispatchEvent(event, listeners);
				return;
			}
		}
	}
	
	abstract Class<TEvent> getEventClass();
	abstract Class<TListeners> getListenerClass();

	@Override
	public void dispose()
	{
		if (listenersList != null)
		{
			listenersList.clear();
			listenersList = null;
		}
		
		if (classList != null)
		{
			classList.clear();
			classList = null;
		}
		
		if (enumList != null)
		{
			enumList.clear();
			enumList = null;
		}
		
		size = -1;
	}

	protected abstract void dispatchEvent(TEvent event, ArrayList<TListeners> listeners);
	
}
