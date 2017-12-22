package zyx.engine.components.screen;

import zyx.engine.utils.callbacks.CustomCallback;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.textures.AbstractTexture;

public class Image extends DisplayObject implements IResourceLoaded<AbstractTexture>
{

	private String path;
	private ScreenModel model;
	public boolean loaded;

	private float originalWidth;
	private float originalHeight;

	public CustomCallback<Image> onLoaded;

	public Image()
	{
		originalWidth = 0;
		originalHeight = 0;
		loaded = false;
		onLoaded = new CustomCallback<>(true);
	}

	public void load(String path)
	{
		this.path = path;
		TextureManager.getInstance().loadTexture(path, this);
	}

	@Override
	public void resourceLoaded(AbstractTexture texture)
	{
		model = new ScreenModel(texture);

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
	}

	@Override
	public String toString()
	{
		return String.format("Image{%s}", path);
	}
}
