package zyx.game.controls.resourceloader.requests;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class ResourceRequestDataInput extends ResourceRequest<ResourceDataInputStream>
{

	private ResourceDataInputStream data;

	public ResourceRequestDataInput(String path, IResourceLoaded<ResourceDataInputStream> callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		data = new ResourceDataInputStream(bytes);
	}

	@Override
	public ResourceDataInputStream getData()
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
