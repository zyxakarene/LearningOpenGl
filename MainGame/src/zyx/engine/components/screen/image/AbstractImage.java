package zyx.engine.components.screen.image;

import zyx.engine.components.animations.ILoadable;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.opengl.textures.AbstractTexture;

public abstract class AbstractImage extends AbstractQuad implements ILoadable, IResourceReady<TextureResource>
{

	public CustomCallback<AbstractImage> onLoaded;

	protected String resource;
	
	private Resource textureResource;

	public AbstractImage()
	{
		onLoaded = new CustomCallback<>();
	}

	@Override
	public void load(String resource)
	{
		this.resource = resource;

		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}

	@Override
	public final void onResourceReady(TextureResource resource)
	{
		AbstractTexture texture = resource.getContent();

		onTextureResourceReady(texture);

		onLoaded.dispatch(this);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}

		if (onLoaded != null)
		{
			onLoaded.dispose();
			onLoaded = null;
		}
	}

	protected abstract void onTextureResourceReady(AbstractTexture texture);

}
