package zyx.engine.utils.callbacks;

import java.util.ArrayList;
import java.util.Iterator;
import zyx.utils.interfaces.IDisposeable;

public class CustomCallback<T> implements IDisposeable
{
	private ArrayList<ICallback<T>> callbacks;
	private final boolean autoClear;

	public CustomCallback()
	{
		this(false);
	}
	
	public CustomCallback(boolean autoClear)
	{
		this.autoClear = autoClear;
		this.callbacks = new ArrayList<>();
	}
	
	public void addCallback(ICallback<T> callback)
	{
		if (callbacks.contains(callback) == false)
		{
			callbacks.add(callback);
		}
	}
	
	public void removeCallback(ICallback<T> callback)
	{
		callbacks.remove(callback);
	}
	
	public boolean hasEntries()
	{
		return callbacks.size() > 0;
	}
	
	public void dispatch(T data)
	{
		int len = callbacks.size();
		for (int i = 0; i < len; i++)
		{
			ICallback<T> callback = callbacks.get(i);
			if (callback != null)
			{
				callback.onCallback(data);
			}
		}
		
		if (autoClear)
		{
			callbacks.clear();
		}
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		callbacks = null;
	}
}
