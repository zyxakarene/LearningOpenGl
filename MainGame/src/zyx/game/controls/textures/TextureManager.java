package zyx.game.controls.textures;

import java.util.*;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;

public class TextureManager implements IDisposeable
{

	private static final TextureManager INSTANCE = new TextureManager();

	private HashMap<String, TextureCacheEntry> cache;
	private HashMap<String, TextureRequest> currentRequests;

	private TextureManager()
	{
		cache = new HashMap<>();
		currentRequests = new HashMap<>();
	}

	public static TextureManager getInstance()
	{
		return INSTANCE;
	}

	public void loadTexture(String path, IResourceLoaded<AbstractTexture> callback)
	{
		if (cache.containsKey(path))
		{
			TextureCacheEntry entry = cache.get(path);
			entry.count += 1;
			
			callback.resourceLoaded(entry.texture);
		}
		else if (currentRequests.containsKey(path))
		{
			TextureRequest request = currentRequests.get(path);
			request.addCallback(callback);
		}
		else
		{
			TextureRequest request = new TextureRequest(path, callback);
			currentRequests.put(path, request);
		}
	}

	public void removeTexture(String name)
	{
		cache.remove(name);
	}

	void textureLoaded(String path, AbstractTexture texture)
	{
		TextureCacheEntry cacheEntry = new TextureCacheEntry(texture);
		cache.put(path, cacheEntry);
		
		TextureRequest request = currentRequests.remove(path);
		request.dispose();
	}
	
	@Override
	public void dispose()
	{		
		String key;
		Iterator<String> keys;
		TextureRequest requestEntry;
		TextureCacheEntry cacheEntry;
				
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
