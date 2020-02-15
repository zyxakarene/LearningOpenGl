package zyx.game.controls.resourceloader.requests;

import java.io.InputStream;
import java.util.ArrayList;
import zyx.utils.interfaces.IDisposeable;

public abstract class ResourceRequest<T extends InputStream> implements IDisposeable
{
	public String path;
	public ArrayList<IResourceLoaded<T>> callbacks;
	public boolean requestCompleted;

	ResourceRequest(String path, IResourceLoaded<T> callback)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();
		this.callbacks.add(callback);
		this.requestCompleted = true;
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
		return path.hashCode();
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
	}

	public void mergeFrom(ResourceRequest request)
	{
		callbacks.addAll(request.callbacks);
	}

	public void unMergeFrom(ResourceRequest request)
	{
		callbacks.removeAll(request.callbacks);
	}
	
	public void complete(T data)
	{
		if(callbacks != null)
		{
			onPostComplete();
			
			for (IResourceLoaded<T> callback : callbacks)
			{
				callback.resourceLoaded(data);
			}
		}
	}

	protected void onPostComplete()
	{
	}
}
