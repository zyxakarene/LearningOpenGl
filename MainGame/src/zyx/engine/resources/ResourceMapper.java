package zyx.engine.resources;

import zyx.engine.resources.impl.sheet.SpriteSheetResource;
import zyx.engine.resources.impl.sheet.SpriteSheetJsonResource;
import zyx.engine.resources.impl.meshes.MeshBatchResource;
import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.engine.resources.impl.textures.SpecularTextureResource;
import zyx.engine.resources.impl.textures.NormalTextureResource;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.engine.resources.impl.*;
import zyx.engine.resources.impl.meshes.SkeletonResource;
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
		else if (resourceClass == SkeletonResource.class)
		{
			return new SkeletonResource(path);
		}
		else if (resourceClass == SoundResource.class)
		{
			return new SoundResource(path);
		}
		else if (resourceClass == TextureResource.class)
		{
			return new TextureResource(path);
		}
		else if (resourceClass == NormalTextureResource.class)
		{
			return new NormalTextureResource(path);
		}
		else if (resourceClass == SpecularTextureResource.class)
		{
			return new SpecularTextureResource(path);
		}
		else if (resourceClass == ParticleResource.class)
		{
			return new ParticleResource(path);
		}
		else if (resourceClass == FontResource.class)
		{
			return new FontResource(path);
		}
		else if (resourceClass == SpriteSheetResource.class)
		{
			return new SpriteSheetResource(path);
		}
		else if (resourceClass == JsonResource.class)
		{
			return new JsonResource(path);
		}
		else if (resourceClass == SpriteSheetJsonResource.class)
		{
			return new SpriteSheetJsonResource(path);
		}
		else if (resourceClass == CubemapResource.class)
		{
			return new CubemapResource(path);
		}
		else if (resourceClass == SkyboxResource.class)
		{
			return new SkyboxResource(path);
		}
		else if (resourceClass == MeshBatchResource.class)
		{
			return new MeshBatchResource(path);
		}
		else if (resourceClass == DrawableTextureResource.class)
		{
			return new DrawableTextureResource(path);
		}
		
		return new GenericResource(path);
	}

}
