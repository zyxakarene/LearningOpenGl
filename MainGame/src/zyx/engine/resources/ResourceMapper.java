package zyx.engine.resources;

import zyx.engine.resources.impl.*;
import zyx.engine.resources.rules.ParsedResource;
import zyx.engine.resources.rules.ResourceRuleParser;

class ResourceMapper
{

	private ResourceRuleParser resourceParser;

	ResourceMapper()
	{
		resourceParser = new ResourceRuleParser();
		resourceParser.addRules();
	}

	Resource getFromResource(String resource)
	{
		ParsedResource response = resourceParser.parseResource(resource);
		
		String path = response.path;
		Class resourceClass = response.rule.resource;
		if (resourceClass == MeshResource.class)
		{
			return new MeshResource(path);
		}
		else if (resourceClass == SoundResource.class)
		{
			return new SoundResource(path);
		}
		else if (resourceClass == TextureResource.class)
		{
			return new TextureResource(path);
		}
		else if (resourceClass == ParticleResource.class)
		{
			return new ParticleResource(path);
		}
		
		return new GenericResource(path);
	}

}
