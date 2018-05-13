package zyx.game.controls.loading;

import java.util.ArrayList;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractRequest<T extends IDisposeable> implements IResourceLoaded<ResourceDataInputStream>, IDisposeable
{

	protected AbstractLoader<T> loader;
	protected String path;
	
	private ArrayList<IResourceLoaded<T>> callbacks;
	
	protected AbstractRequest(String path, IResourceLoaded<T>callback, AbstractLoader<T> factory)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();
		this.loader = factory;
		
		callbacks.add(callback);
	}

	final void addCallback(IResourceLoaded<T> callback)
	{
		callbacks.add(callback);
	}

	public void beginLoad()
	{
		ResourceRequest request = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addRequest(request);
	}
	
	@Override
	public abstract void resourceLoaded(ResourceDataInputStream data);
	
	protected final void onLoadComplete(Object[] params)
	{
		T instance = loader.createNewInstance(params);
		
		ArrayList<IResourceLoaded<T>> clone = (ArrayList<IResourceLoaded<T>>) callbacks.clone();
		for (IResourceLoaded<T> callback : clone)
		{
			callback.resourceLoaded(instance);
		}

		loader.loadComplete(path, instance);
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		
		path = null;
		callbacks = null;
	}
}
