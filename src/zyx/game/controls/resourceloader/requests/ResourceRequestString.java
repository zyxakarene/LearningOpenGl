package zyx.game.controls.resourceloader.requests;

public class ResourceRequestString extends ResourceRequest
{
	private String data;

	public ResourceRequestString(String path, IResourceLoaded<Object> callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		data = new String(bytes);
	}

	@Override
	public Object getData()
	{
		return data;
	}
}
