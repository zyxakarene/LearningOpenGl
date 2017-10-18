package dev.resourceloader.requests;

import dev.resourceloader.IResourceLoaded;

public abstract class ResourceRequest
{
	public String path;
	public IResourceLoaded callback;

	public ResourceRequest(String path, IResourceLoaded callback)
	{
		this.path = path;
		this.callback = callback;
	}
	
	public abstract void setData(byte[] data);
	public abstract Object getData();
	
}
