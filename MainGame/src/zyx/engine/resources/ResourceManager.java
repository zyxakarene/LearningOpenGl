package zyx.engine.resources;

import zyx.engine.resources.rules.ResourceRuleParser;

public class ResourceManager
{
	private static ResourceManager instance = new ResourceManager();
	
	private ResourceRuleParser resourceParser;
	
	private ResourceManager()
	{
		resourceParser = new ResourceRuleParser();
		resourceParser.addRules();
	}

	public static ResourceManager getInstance()
	{
		return instance;
	}

	public void getResource(String resource)
	{
		String path = resourceParser.parseResource(resource);
		System.out.println(path);
	}
	
}
