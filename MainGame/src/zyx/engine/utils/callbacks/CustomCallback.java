package zyx.engine.utils.callbacks;

import java.util.ArrayList;
import zyx.utils.interfaces.IDisposeable;

public class CustomCallback<T> implements IDisposeable
{
	private ArrayList<ICallback<T>> callbacks;

	public CustomCallback()
	{
		callbacks = new ArrayList<>();
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
		ICallback<T> callback;
		
		int len = callbacks.size();
		for (int i = 0; i < len; i++)
		{
			callback = callbacks.get(i);
			callback.onCallback(data);
		}
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		callbacks = null;
	}
}
