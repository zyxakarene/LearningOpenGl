package zyx.engine.resources.impl;

import java.util.ArrayList;
import zyx.debug.views.base.IDebugIcon;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.IResourceReloaded;
import zyx.engine.resources.ResourceManager;

public abstract class Resource implements IDebugIcon
{

	public final String path;

	protected Object content;

	private ArrayList<IResourceReady> pointers;
	private boolean loading;
	private boolean loaded;

	private ArrayList<Resource> dependencies;
	private int loadedDependenciesCount;
	private IResourceReady dependencyLoaded;

	private ArrayList<IResourceReloaded> reloadedListeners;

	Resource(String path)
	{
		this.path = path;
		this.loaded = false;

		this.pointers = new ArrayList<>();
		this.dependencies = new ArrayList<>();
		this.reloadedListeners = new ArrayList<>();
		this.loadedDependenciesCount = 0;

		this.dependencyLoaded = (IResourceReady) this::onDependencyLoaded;
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

			if (callback instanceof IResourceReloaded)
			{
				reloadedListeners.add((IResourceReloaded) callback);
			}
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

	private void beginLoad()
	{
		onBeginLoad();

		for (Resource dependency : dependencies)
		{
			dependency.registerAndLoad(dependencyLoaded);
		}
	}

	protected abstract void onBeginLoad();

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
		
		if (removed)
		{
			if (callback instanceof IResourceReloaded)
			{
				reloadedListeners.remove((IResourceReloaded) callback);
			}
			
			if (pointers.isEmpty())
			{
				dispose();

				loaded = false;
				loading = false;
			}
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

	public boolean isLoaded()
	{
		return loaded;
	}

	public boolean isLoading()
	{
		return loading;
	}

	@Override
	public String getDebugIcon()
	{
		return "default.png";
	}

	@Override
	public String getDebugIconFolder()
	{
		return "resource";
	}
	
	public void forceRefresh()
	{
		resourceReloaded();
	}

	protected final void resourceReloaded()
	{
		for (IResourceReloaded listener : reloadedListeners)
		{
			listener.onResourceReloaded(this);
		}
	}

	protected void onDispose()
	{

	}
}
