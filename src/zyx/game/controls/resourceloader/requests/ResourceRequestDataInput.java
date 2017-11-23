package zyx.game.controls.resourceloader.requests;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class ResourceRequestDataInput extends ResourceRequest
{
	private DataInputStream data;

	public ResourceRequestDataInput(String path, IResourceLoaded<Object> callback)
	{
		super(path, callback);
	}

	@Override
	public void setData(byte[] bytes)
	{
		data = new DataInputStream(new ByteArrayInputStream(bytes));
	}

	@Override
	public Object getData()
	{
		return data;
	}
}
