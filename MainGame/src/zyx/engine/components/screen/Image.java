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

public class Image extends DisplayObject implements IResourceReady<TextureResource>
{

	protected static final Vector4f COLORS = SharedShaderObjects.SHARED_VECTOR_4F;

	private Resource textureResource;

	private String resource;
	private ScreenModel model;
	public boolean loaded;

	private float originalWidth;
	private float originalHeight;
	private Vector4f colors;

	public CustomCallback<Image> onLoaded;

	public Image()
	{
		colors = new Vector4f(1, 1, 1, 1);
		originalWidth = 0;
		originalHeight = 0;
		loaded = false;
		onLoaded = new CustomCallback<>(true);
	}

	public void load(String resource)
	{
		this.resource = resource;

		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}

	public void setColor(Vector4f color)
	{
		colors.set(color);
		updateMesh();
	}
	
	public void setColor(float r, float g, float b)
	{
		colors.set(r, g, b);
		updateMesh();
	}

	public void setAlpha(float a)
	{
		colors.w = a;
		updateMesh();
	}
	
	private void updateMesh()
	{
		if(loaded)
		{
			model.setColors(colors);
		}
	}
	
	@Override
	public void onResourceReady(TextureResource resource)
	{
		GameTexture texture = resource.getContent();

		model = new ScreenModel(texture, texture.getWidth(), texture.getHeight(), colors);

		originalWidth = getWidth();
		originalHeight = getHeight();
		loaded = true;

		onLoaded.dispatch(this);
	}

	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return model.getWidth() * scale.x;
		}

		return 0;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return model.getHeight() * scale.y;
		}

		return 0;
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			scale.x = value / originalWidth;
		}
	}

	@Override
	public void setHeight(float value)
	{
		if (loaded)
		{
			scale.y = value / originalHeight;
		}
	}

	@Override
	void onDraw()
	{
		transform();
		shader.upload();

		if (loaded)
		{
			model.draw();
		}
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
