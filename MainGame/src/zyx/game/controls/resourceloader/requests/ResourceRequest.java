package zyx.game.controls.resourceloader.requests;

import java.io.InputStream;
import java.util.ArrayList;
import zyx.utils.interfaces.IDisposeable;

public abstract class ResourceRequest<T extends InputStream> implements IDisposeable
{
	public String path;
	public ArrayList<IResourceLoaded<T>> callbacks;
	public ArrayList<IResourceFailed> failedCallbacks;
	public boolean requestCompleted;

	ResourceRequest(String path, IResourceLoaded<T> callback, IResourceFailed failedCallback)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();
		this.callbacks.add(callback);
		this.requestCompleted = true;
		
		failedCallbacks = new ArrayList<>();
		if (failedCallback != null)
		{
			failedCallbacks.add(failedCallback);
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ResourceRequest)
		{
			ResourceRequest other = (ResourceRequest) obj;
			return this.path != null && this.path.equals(other.path);
		}
		
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode() + path.hashCode();
	}
	
	public void setFailed()
	{
		requestCompleted = false;
	}
	
	public abstract void setData(byte[] data);
	public abstract T getData();

	@Override
	public void dispose()
	{
		path = null;
		
		if(callbacks != null)
		{
			callbacks.clear();
			callbacks = null;
		}
		
		if (failedCallbacks != null)
		{
			failedCallbacks.clear();
			failedCallbacks = null;
		}
	}

	public void mergeFrom(ResourceRequest<T> request)
	{
		for (IResourceLoaded<T> newCallbacks : request.callbacks)
		{
			if (callbacks.indexOf(newCallbacks) == -1)
			{
				callbacks.add(newCallbacks);
			}
			else
			{
				//TODO: Figure out why the same callback sometimes already exists
			}
		}
		
		for (IResourceFailed newCallbacks : request.failedCallbacks)
		{
			if (failedCallbacks.indexOf(newCallbacks) == -1)
			{
				failedCallbacks.add(newCallbacks);
			}
			else
			{
				//TODO: Figure out why the same callback sometimes already exists
			}
		}
	}

	public void unMergeFrom(ResourceRequest<T> request)
	{
		callbacks.removeAll(request.callbacks);
		failedCallbacks.removeAll(request.failedCallbacks);
	}
	
	public void complete(T data)
	{
		if(callbacks != null)
		{
			for (IResourceLoaded<T> callback : callbacks)
			{
				callback.resourceLoaded(data);
			}
		}
	}
	
	public void fail()
	{
		if(failedCallbacks != null)
		{
			for (IResourceFailed callback : failedCallbacks)
			{
				callback.onResourceFailed(path);
			}
		}
	}
}
