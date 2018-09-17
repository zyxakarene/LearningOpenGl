package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.TextureResource;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.textures.GameTexture;

public class Image extends AbstractQuad implements IResourceReady<TextureResource>
{

	protected static final Vector4f COLORS = SharedShaderObjects.SHARED_VECTOR_4F;

	private Resource textureResource;

	private String resource;

	public CustomCallback<Image> onLoaded;

	public Image()
	{
		setColor(1, 1, 1);
		
		onLoaded = new CustomCallback<>(true);
	}

	public void load(String resource)
	{
		this.resource = resource;

		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}
	
	@Override
	public void onResourceReady(TextureResource resource)
	{
		GameTexture texture = resource.getContent();

		model = new ScreenModel(texture, texture.getWidth(), texture.getHeight(), colors);

		loaded = true;
		originalWidth = getWidth();
		originalHeight = getHeight();

		onLoaded.dispatch(this);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (loaded)
		{
			model.dispose();
			model = null;
		}

		if (textureResource != null)
		{
			textureResource.unregister(this);
			textureResource = null;
		}
	}

	@Override
	public String toString()
	{
		return String.format("Image{%s}", resource);
	}
}
