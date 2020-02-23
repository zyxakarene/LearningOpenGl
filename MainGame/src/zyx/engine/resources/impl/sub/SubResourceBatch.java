package zyx.engine.resources.impl.sub;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.interfaces.IDisposeable;

public class SubResourceBatch<T> implements IResourceReady<Resource>, IDisposeable
{

	private int resourcesLoadedCounter;
	
	private Resource[] resourceArray;
	private HashMap<Resource, Integer> resourceToIndexMap;
	private ArrayList<T> contentList;
	
	private ISubResourceLoaded<T> callback;
	private String[] resourceIds;
	
	private ICallback onAllDone;

	public SubResourceBatch(ISubResourceLoaded<T> callback, String... resourceIds)
	{
		this.callback = callback;
		this.resourcesLoadedCounter = 0;
		this.resourceToIndexMap = new HashMap<>();
		this.contentList = new ArrayList<>();
		this.resourceIds = resourceIds;
	}

	void load(ICallback<SubResourceBatch> onAllDone)
	{
		this.onAllDone = onAllDone;
		
		int len = resourceIds.length;
		resourceArray = new Resource[len];
		contentList = new ArrayList<>(len);
		
		for (int i = 0; i < len; i++)
		{
			String resourceString = resourceIds[i];
			Resource resource = ResourceManager.getInstance().getResource(resourceString);
			resourceArray[i] = resource;
			
			resourceToIndexMap.put(resource, i);
			contentList.add(null);
		}

		for (int i = 0; i < len; i++)
		{
			Resource resource = resourceArray[i];
			resource.registerAndLoad(this);
		}
	}

	@Override
	public void onResourceReady(Resource resource)
	{
		Integer index = resourceToIndexMap.get(resource);
		T content = (T) resource.getContent();
		contentList.set(index, content);
		
		resourcesLoadedCounter++;
		
		if (resourcesLoadedCounter == resourceArray.length)
		{
			callback.onLoaded(contentList);
			
			onAllDone.onCallback(this);
		}
	}

	@Override
	public void dispose()
	{
		if (callback != null)
		{
			for (Resource resource : resourceArray)
			{
				resource.unregister(this);
			}
			
			contentList.clear();
			resourceToIndexMap.clear();
			
			contentList = null;
			resourceToIndexMap = null;
			resourceArray = null;
			resourceIds = null;
			
			callback = null;
		}
	}
}
