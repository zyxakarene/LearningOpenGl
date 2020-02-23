package zyx.engine.resources;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import zyx.engine.resources.impl.*;
import zyx.engine.resources.rules.ParsedResource;
import zyx.engine.resources.rules.ResourceRuleParser;

class ResourceMapper
{

	private ResourceRuleParser resourceParser;
	private HashMap<Class<? extends Resource>, Constructor<? extends Resource>> constructorMap;

	ResourceMapper()
	{
		resourceParser = new ResourceRuleParser();
		resourceParser.addRules();
		
		constructorMap = new HashMap<>();
	}

	Resource getFromResource(String resource)
	{
		ParsedResource response = resourceParser.parseResource(resource);
		
		Resource instance = createInstanceFrom(response);
		return instance;
	}
	
	private Resource createInstanceFrom(ParsedResource parsedResource)
	{
		Class<? extends Resource> clazz = parsedResource.rule.resource;
		Constructor<? extends Resource> constructor = constructorMap.get(clazz);
		
		if (constructor == null)
		{
			try
			{
				constructor = clazz.getConstructor(String.class);
				constructorMap.put(clazz, constructor);
			}
			catch (NoSuchMethodException | SecurityException ex)
			{
				String msg = String.format("Could not get reflected constructor from class %s because of exception.\n"
						+ "It is probably missing the String constructor", clazz.getSimpleName());
				throw new RuntimeException(msg);
			}
		}
		
		try
		{
			String path = parsedResource.path;
			Resource instance = constructor.newInstance(path);
			
			return instance;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
		{
			String msg = String.format("Could not get create new instance from class %s", clazz.getSimpleName());
			throw new RuntimeException(msg);
		}
	}

}
