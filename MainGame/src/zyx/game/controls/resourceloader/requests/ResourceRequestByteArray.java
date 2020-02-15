package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;

public class ResourceRequestByteArray extends ResourceRequest<ResourceByteArray>
{
	private byte[] bytes;

	public ResourceRequestByteArray(String path, IResourceLoaded<ResourceByteArray> callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		this.bytes = bytes;
	}

	@Override
	public ResourceByteArray getData()
	{
		return new ResourceByteArray(bytes);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if (bytes != null)
		{
			bytes = null;
		}
	}
}
