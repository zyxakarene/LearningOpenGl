package zyx.game.controls.models;

import java.util.*;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.WorldModel;
import zyx.utils.interfaces.IDisposeable;

public class ModelManager implements IModelLoaded, IDisposeable
{

	private static final ModelManager INSTANCE = new ModelManager();

	private HashMap<String, WorldModel> cache;
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
			callback.resourceLoaded(cache.get(path));
		}
		else if (currentRequests.containsKey(path))
		{
			ModelRequest request = currentRequests.get(path);
			request.addCallback(callback);
		}
		else
		{
			ModelRequest request = new ModelRequest(path, this, callback);
			currentRequests.put(path, request);
		}
	}

	@Override
	public void modelLoaded(String path, WorldModel model)
	{
		ModelRequest request = currentRequests.remove(path);
		request.dispose();
	}

	@Override
	public void dispose()
	{		
		String key;
		Iterator<String> keys;
		ModelRequest requestEntry;
		WorldModel cacheEntry;
				
		keys = currentRequests.keySet().iterator();
		while (keys.hasNext())
		{
			key = keys.next();
			requestEntry = currentRequests.remove(key);
			
			requestEntry.dispose();
		}
		
		keys = cache.keySet().iterator();
		while (keys.hasNext())
		{
			key = keys.next();
			cacheEntry = cache.remove(key);
			
			cacheEntry.dispose();
		}
		
		currentRequests.clear();
		cache.clear();
		
		cache = null;
		currentRequests = null;
	}
}
