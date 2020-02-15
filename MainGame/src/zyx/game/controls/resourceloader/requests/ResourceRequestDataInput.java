package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class ResourceRequestDataInput extends ResourceRequest<ResourceDataInputStream>
{

	private byte[] bytes;

	public ResourceRequestDataInput(String path, IResourceLoaded<ResourceDataInputStream> callback)
	{
		super(path, callback);
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
