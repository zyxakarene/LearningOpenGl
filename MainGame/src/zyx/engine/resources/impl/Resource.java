package zyx.engine.resources.impl;

import java.util.ArrayList;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public abstract class Resource implements IResourceLoaded<ResourceDataInputStream>
{

	public final String path;

	protected Object content;

	private ArrayList<IResourceReady> pointers;
	private boolean loading;
	private boolean loaded;

	private ResourceRequestDataInput resourceRequest;
	
	private ArrayList<Resource> dependencies;
	private int loadedDependenciesCount;
	private IResourceReady dependencyLoaded;

	Resource(String path)
	{
		this.path = path;
		this.loaded = false;

		this.pointers = new ArrayList<>();
		this.dependencies = new ArrayList<>();
		this.loadedDependenciesCount = 0;
		
		this.dependencyLoaded = (IResourceReady) (Resource resource) ->
		{
			onDependencyLoaded(resource);
		};
	}

	public void addDependency(Resource dependency)
	{
		dependencies.add(dependency);
	}
	
	public void registerAndLoad(IResourceReady callback)
	{
		DebugResourceList.addResource(this);

		if (pointers.contains(callback) == false)
		{
			pointers.add(callback);
		}

		if (loaded)
		{
			callback.onResourceReady(this);
		}
		else if (!loading)
		{
			loading = true;

			beginLoad();
		}
	}

	protected void beginLoad()
	{
		resourceRequest = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addEntry(resourceRequest);
		
		for (Resource dependency : dependencies)
		{
			dependency.registerAndLoad(dependencyLoaded);
		}
	}
	
	private void onDependencyLoaded(Resource resource)
	{
		loadedDependenciesCount++;
		
		if (content != null)
		{
			onContentLoaded(content);
		}
	}
	
	public void unregister(IResourceReady callback)
	{
		boolean removed = pointers.remove(callback);
		if (removed && pointers.isEmpty())
		{
			dispose();

			loaded = false;
			loading = false;
		}
	}

	public Object getContent()
	{
		return content;
	}

	protected void onContentLoaded(Object content)
	{
		this.content = content;
		
		if (loadedDependenciesCount == dependencies.size())
		{
			this.loaded = true;

			IResourceReady pointer;
			for (int i = pointers.size() - 1; i >= 0; i--)
			{
				pointer = pointers.get(i);
				pointer.onResourceReady(this);
			}
		}
	}

	private void dispose()
	{
		if (loading && !loaded && resourceRequest != null)
		{
			ResourceLoader.getInstance().cancelEntry(resourceRequest);
			resourceRequest.dispose();
			resourceRequest = null;
		}

		for (Resource dependency : dependencies)
		{
			dependency.unregister(dependencyLoaded);
		}
		
		onDispose();

		if (pointers.isEmpty() == false)
		{
			throw new RuntimeException("Resource still has active pointers when disposed!");
		}

		content = null;

		DebugResourceList.removeResource(this);
		
		ResourceManager.getInstance().disposeResource(path);
	}

	void onDispose()
	{

	}
}
