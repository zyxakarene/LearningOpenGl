package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;

public class ResourceRequestByteArray extends ResourceRequest<ResourceByteArray>
{
	private ResourceByteArray data;

	public ResourceRequestByteArray(String path, IResourceLoaded<ResourceByteArray> callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		data = new ResourceByteArray(bytes);
	}

	@Override
	public ResourceByteArray getData()
	{
		return data;
	}
	
	@Override
	protected void onPostComplete()
	{
		if (data.markSupported())
		{
			data.reset();
		}
	}
}
