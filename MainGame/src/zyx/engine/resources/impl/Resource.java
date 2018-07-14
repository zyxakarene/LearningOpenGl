package zyx.engine.resources.impl;

import java.util.ArrayList;
import zyx.engine.resources.IResourceReady;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.utils.cheats.Print;

public abstract class Resource implements IResourceLoaded<ResourceDataInputStream>
{

	public final String path;

	protected Object content;

	private ArrayList<IResourceReady> pointers;
	private boolean loading;
	private boolean loaded;

	public Resource(String path)
	{
		this.path = path;
		this.loaded = false;

		this.pointers = new ArrayList<>();
	}

	public void registerAndLoad(IResourceReady callback)
	{
		if (pointers.contains(callback) == false)
		{
			pointers.add(callback);
		}

		if (loaded)
		{
			callback.onResourceReady(this);
		}
		else
		{
			if (!loading)
			{
				loading = true;
				ResourceRequest request = new ResourceRequestDataInput(path, this);
				ResourceLoader.getInstance().addRequest(request);
			}
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
		this.loaded = true;
		this.content = content;

		for (IResourceReady pointer : pointers)
		{
			pointer.onResourceReady(this);
		}
	}

	private void dispose()
	{
		onDispose();
		
		if (pointers.isEmpty() == false)
		{
			throw new RuntimeException("Resource still has active pointers when disposed!");
		}
		
		content = null;
	}
	
	void onDispose()
	{
		
	}
}