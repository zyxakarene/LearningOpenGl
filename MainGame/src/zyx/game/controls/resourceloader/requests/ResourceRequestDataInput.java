package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class ResourceRequestDataInput extends ResourceRequest<ResourceDataInputStream>
{

	private byte[] bytes;

	public ResourceRequestDataInput(String path, IResourceLoaded<ResourceDataInputStream> callback)
	{
		this(path, callback, null);
	}

	public ResourceRequestDataInput(String path, IResourceLoaded<ResourceDataInputStream> callback, IResourceFailed failedCallback)
	{
		super(path, callback, failedCallback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		this.bytes = bytes;
	}

	@Override
	public ResourceDataInputStream getData()
	{
		return new ResourceDataInputStream(bytes);
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
