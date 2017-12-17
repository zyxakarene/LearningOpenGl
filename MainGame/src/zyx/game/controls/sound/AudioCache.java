package zyx.game.controls.sound;

import java.util.HashMap;
import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;

class AudioCache
{
	private static HashMap<String, ResourceByteArray> CACHE = new HashMap<>();
	
	static ResourceByteArray getFromCache(String path)
	{
		return CACHE.get(path);
	}

	static void addToCache(String path, ResourceByteArray data)
	{
		CACHE.put(path, data);
	}
}
