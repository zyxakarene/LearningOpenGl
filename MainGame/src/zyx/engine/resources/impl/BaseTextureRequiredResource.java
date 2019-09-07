package zyx.engine.resources.impl;

import java.util.HashMap;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.opengl.textures.AbstractTexture;

public abstract class BaseTextureRequiredResource extends Resource implements IResourceReady<TextureResource>
{

	private Resource[] textureResources;
	
	private HashMap<Resource, Integer> resourceToIndexMap;
	private AbstractTexture[] loadedTextures;

	public BaseTextureRequiredResource(String path)
	{
		super(path);
	}

	@Override
	void onDispose()
	{
		super.onDispose();
		
		if(textureResources != null)
		{
			for (Resource resource : textureResources)
			{
				resource.unregister(this);
			}
			
			textureResources = null;
		}
	}

	protected void loadTextures(String... resources)
	{
		resourceToIndexMap = new HashMap<>();
		textureResources = new TextureResource[resources.length];
		loadedTextures = new AbstractTexture[resources.length];
		
		Resource[] toLoad = new Resource[resources.length];
		
		for (int i = 0; i < textureResources.length; i++)
		{
			String resource = resources[i];
			
			Resource textureResource = ResourceManager.getInstance().getResource(resource);
			
			textureResources[i] = textureResource;
			
			resourceToIndexMap.put(textureResource, i);
			toLoad[i] = textureResource;
		}
		
		for (Resource resource : toLoad)
		{
			resource.registerAndLoad(this);
		}
	}
	
	@Override
	public void onResourceReady(TextureResource resource)
	{
		int index = resourceToIndexMap.remove(resource);
		
		loadedTextures[index] = resource.getContent();
		
		if (resourceToIndexMap.isEmpty())
		{
			onTexturesLoaded(loadedTextures);
		}
	}

	protected abstract void onTexturesLoaded(AbstractTexture[] texture);
}