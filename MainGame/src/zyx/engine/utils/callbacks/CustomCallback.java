package zyx.engine.utils.callbacks;

import java.util.ArrayList;
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
		for (ICallback<T> callback : callbacks)
		{
			callback.onCallback(data);
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
