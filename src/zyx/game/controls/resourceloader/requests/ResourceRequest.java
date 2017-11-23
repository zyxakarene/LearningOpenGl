package zyx.game.controls.resourceloader.requests;

public abstract class ResourceRequest
{
	public String path;
	public IResourceLoaded<Object> callback;

	public ResourceRequest(String path, IResourceLoaded<Object> callback)
	{
		this.path = path;
		this.callback = callback;
	}
	
	public abstract void setData(byte[] data);
	public abstract Object getData();
	
}
