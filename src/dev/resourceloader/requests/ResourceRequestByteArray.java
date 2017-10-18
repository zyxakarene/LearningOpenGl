package dev.resourceloader.requests;

import dev.resourceloader.IResourceLoaded;
import dev.resourceloader.ResourceByteArray;

public class ResourceRequestByteArray extends ResourceRequest
{
	private ResourceByteArray data;

	public ResourceRequestByteArray(String path, IResourceLoaded callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		data = new ResourceByteArray(bytes);
	}

	@Override
	public Object getData()
	{
		return data;
	}
}
