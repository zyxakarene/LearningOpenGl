package zyx.engine.components.screen.composed;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.image.AbstractImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;

public class ComposedImage extends DisplayObjectContainer implements IComposedImage
{
	
	private int[] colors;
	private AbstractImage[] images;
	private boolean scale9;
	
	public ComposedImage(boolean scale9)
	{
		this.scale9 = scale9;
	}
	
	@Override
	public void setTextures(String[] textures)
	{
		clean();
		
		images = new AbstractImage[textures.length];
		
		String texture;
		AbstractImage image;
		for (int i = 0; i < textures.length; i++)
		{
			if (scale9)
			{
				image = new Scale9Image();
			}
			else
			{	
				image = new Image();
			}
			
			addChild(image);
			
			texture = textures[i];
			image.load(texture);
			
			images[i] = image;
		}
		
		if (colors != null)
		{
			applyColors();
		}
	}
	
	public void setColors(int[] colors)
	{
		this.colors = colors;
		
		if (images != null)
		{
			applyColors();
		}
	}
	
	private void applyColors()
	{
		if (images.length != colors.length)
		{
			String msg = String.format("Length of composed Images and Colors don't match up for %s: %s - %s", name, images.length, colors.length);
			throw new RuntimeException(msg);
		}
		
		for (int i = 0; i < images.length; i++)
		{
			int color = colors[i];
			images[i].setColor(color);
		}
	}

	private void clean()
	{
		if (images != null)
		{
			for (AbstractImage image : images)
			{
				image.dispose();
			}
			
			images = null;
		}
		
		colors = null;
	}

	@Override
	public void setColors(Vector3f[] colors)
	{
	}
}
