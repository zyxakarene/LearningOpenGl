package dev.resourceloader.requests;

import dev.resourceloader.requests.vo.ResourceByteArray;

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
