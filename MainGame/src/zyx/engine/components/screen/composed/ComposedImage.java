package zyx.engine.components.screen.composed;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.utils.callbacks.ICallback;

public class ComposedImage extends DisplayObjectContainer implements IComposedImage
{
	
	protected float originalWidth;
	protected float originalHeight;

	protected AbstractImage[] images;
	protected boolean loaded;
	private int loadedCount;

	private Vector3f[] colors;
	private float alpha;
	
	private ICallback<AbstractImage> onFirstImageLoaded;
	private boolean scale9;

	public ComposedImage(boolean scale9)
	{
		this.scale9 = scale9;

		alpha = 1;
		focusable = true;

		onFirstImageLoaded = (AbstractImage img) -> 
		{
			onImageLoaded(img);
		};
	}

	@Override
	public void setTextures(String[] textures)
	{
		clean();

		int len = textures.length;
		images = new AbstractImage[len];

		AbstractImage img;
		String texture;
		for (int i = 0; i < len; i++)
		{
			texture = textures[i];
			img = scale9 ? new Scale9Image() : new Image();
			images[i] = img;

			img.onLoaded.addCallback(onFirstImageLoaded);
			
			img.load(texture);
			img.touchable = false;
			addChild(img);
		}
	}

	private void onImageLoaded(AbstractImage img)
	{
		if (loaded == false)
		{
			loaded = true;

			if (originalWidth != 0)
			{
				setWidth(originalWidth);
			}

			if (originalHeight != 0)
			{
				setHeight(originalHeight);
			}
		}
		
		loadedCount++;
		if (loadedCount == images.length)
		{
			updateMesh();
		}
	}

	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return super.getWidth();
		}

		return originalWidth;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return super.getHeight();
		}

		return originalHeight;
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			for (AbstractImage img : images)
			{
				img.setWidth(value);
			}
		}
		else
		{
			originalWidth = value;
		}
	}

	@Override
	public void setHeight(float value)
	{
		if (loaded)
		{
			for (AbstractImage img : images)
			{
				img.setHeight(value);
			}
		}
		else
		{
			originalHeight = value;
		}
	}

	@Override
	public void setColors(Vector3f[] newColors)
	{
		int newLen = newColors.length;
		if (colors == null || colors.length != newLen)
		{
			colors = new Vector3f[newLen];

			for (int i = 0; i < newLen; i++)
			{
				colors[i] = new Vector3f();
			}
		}
		
		for (int i = 0; i < newLen; i++)
		{
			colors[i].set(newColors[i]);
		}
		
		updateMesh();
	}
	
	public void setColor(Vector3f color)
	{
		Vector3f[] singleArray = new Vector3f[]
		{
			color
		};
		
		setColors(singleArray);
	}

	public void setColor(float r, float g, float b)
	{
		HELPER_VEC3.set(r, g, b);
		setColor(HELPER_VEC3);
	}

	public void setAlpha(float a)
	{
		alpha = a;
		updateMesh();
	}

	private void updateMesh()
	{
		if (loaded)
		{
			int len = images.length;
			AbstractImage img;
			Vector3f col;
			for (int i = 0; i < len; i++)
			{
				col = colors[i];
				img = images[i];
				HELPER_VEC4.set(col.x, col.y, col.z, alpha);
				
				img.setColor(HELPER_VEC4);
			}
		}
	}

	@Override
	public void dispose()
	{
		clean();
		
		super.dispose();

		onFirstImageLoaded = null;
	}

	private void clean()
	{
		loadedCount = 0;
		
		if (images != null)
		{
			for (AbstractImage img : images)
			{
				img.dispose();
			}
			images = null;
		}
	}
}
