package zyx.engine.resources;

import zyx.engine.resources.impl.Resource;
import java.util.HashMap;

public class ResourceManager
{
	private static ResourceManager instance = new ResourceManager();
	
	private ResourceMapper mapper;
	private HashMap<String, Resource> resources;
	
	private ResourceManager()
	{
		resources = new HashMap<>();
		mapper = new ResourceMapper();
	}

	public static ResourceManager getInstance()
	{
		return instance;
	}

	public Resource getResource(String resource)
	{
		Resource res = resources.get(resource);
		if (res == null)
		{
			res = mapper.getFromResource(resource);
			resources.put(resource, res);
		}
		
		return res;
	}
}
