package zyx.engine.resources.impl;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.opengl.textures.AbstractTexture;

public abstract class BaseTextureRequiredResource extends Resource implements IResourceReady<TextureResource>
{

	private Resource textureResource;

	public BaseTextureRequiredResource(String path)
	{
		super(path);
	}

	@Override
	void onDispose()
	{
		super.onDispose();
		
		if(textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}
	}

	protected void loadTexture(String resource)
	{
		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}
	
	@Override
	public void onResourceReady(TextureResource resource)
	{
		AbstractTexture texture = resource.getContent();
		onTextureLoaded(texture);
	}

	protected abstract void onTextureLoaded(AbstractTexture texture);
}