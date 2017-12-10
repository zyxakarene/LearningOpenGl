package zyx.game.controls.models;

import java.util.*;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.WorldModel;
import zyx.utils.interfaces.IDisposeable;

public class ModelManager implements IDisposeable
{

	private static final ModelManager INSTANCE = new ModelManager();

	private HashMap<String, ModelCacheEntry> cache;
	private HashMap<String, ModelRequest> currentRequests;

	private ModelManager()
	{
		cache = new HashMap<>();
		currentRequests = new HashMap<>();
	}

	public static ModelManager getInstance()
	{
		return INSTANCE;
	}

	public void loadModel(String path, IResourceLoaded<WorldModel> callback)
	{
		if (cache.containsKey(path))
		{
			ModelCacheEntry entry = cache.get(path);
			entry.count += 1;
			
			callback.resourceLoaded(entry.model);
		}
		else if (currentRequests.containsKey(path))
		{
			ModelRequest request = currentRequests.get(path);
			request.addCallback(callback);
		}
		else
		{
			ModelRequest request = new ModelRequest(path, callback);
			currentRequests.put(path, request);
		}
	}

	void modelLoaded(String path, WorldModel model)
	{
		ModelCacheEntry cacheEntry = new ModelCacheEntry(model);
		cache.put(path, cacheEntry);
		
		ModelRequest request = currentRequests.remove(path);
		request.dispose();
	}
	
	@Override
	public void dispose()
	{		
		String key;
		Iterator<String> keys;
		ModelRequest requestEntry;
		ModelCacheEntry cacheEntry;
				
		keys = currentRequests.keySet().iterator();
		while (keys.hasNext())
		{
			key = keys.next();
			requestEntry = currentRequests.get(key);
			
			requestEntry.dispose();
		}
		
		keys = cache.keySet().iterator();
		while (keys.hasNext())
		{
			key = keys.next();
			cacheEntry = cache.get(key);
			
			cacheEntry.dispose();
		}
		
		currentRequests.clear();
		cache.clear();
	}
}
