package zyx.game.controls.loading;

import java.util.*;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractLoader<T extends IDisposeable> implements IDisposeable
{

	private HashMap<String, CacheEntry<T>> cache;
	private HashMap<String, AbstractRequest<T>> currentRequests;

	protected AbstractLoader()
	{
		cache = new HashMap<>();
		currentRequests = new HashMap<>();
	}

	public void load(String path, IResourceLoaded<T> callback)
	{
		if (cache.containsKey(path))
		{
			CacheEntry<T> entry = cache.get(path);
			entry.count += 1;

			callback.resourceLoaded(entry.model);
		}
		else if (currentRequests.containsKey(path))
		{
			AbstractRequest<T> request = currentRequests.get(path);
			request.addCallback(callback);
		}
		else
		{
			AbstractRequest<T> request = createRequest(path, callback, this);
			currentRequests.put(path, request);

			request.beginLoad();
		}
	}

	void loadComplete(String path, T model)
	{
		CacheEntry<T> cacheEntry = new CacheEntry(model);
		cache.put(path, cacheEntry);

		AbstractRequest<T> request = currentRequests.remove(path);
		request.dispose();
	}

	protected abstract T createNewInstance(Object[] params);

	protected abstract AbstractRequest<T> createRequest(String path, IResourceLoaded<T> callback, AbstractLoader<T> loader);

	@Override
	public void dispose()
	{
		String key;
		Iterator<String> keys;
		AbstractRequest<T> requestEntry;
		CacheEntry<T> cacheEntry;

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
