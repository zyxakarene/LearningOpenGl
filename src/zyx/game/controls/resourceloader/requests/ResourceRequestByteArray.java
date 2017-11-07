package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;

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
