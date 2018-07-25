package zyx.game.controls.resourceloader.requests;

import java.util.ArrayList;
import zyx.utils.interfaces.IDisposeable;

public abstract class ResourceRequest implements IDisposeable
{
	public String path;
	public ArrayList<IResourceLoaded> callbacks;
	public boolean requestCompleted;

	public ResourceRequest(String path, IResourceLoaded callback)
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
			
			return this.path.equals(other.path);
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
	public abstract Object getData();

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
	
	public void complete(Object data)
	{
		if(callbacks != null)
		{
			int len = callbacks.size();
			for (int i = 0; i < len; i++)
			{
				callbacks.get(i).resourceLoaded(data);

				onPostComplete();
			}
		}
	}

	protected void onPostComplete()
	{
	}
}
