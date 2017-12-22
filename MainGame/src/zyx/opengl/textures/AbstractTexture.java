package zyx.opengl.textures;

import org.newdawn.slick.opengl.Texture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractTexture implements IDisposeable
{

	private static final Rectangle SIMPLE_TEXTURE_MAPPING = new Rectangle(0, 0, 1, 1);
	protected static Texture currentlyBoundTexture;

	public final float x, y, u, v;

	private float width;
	private float height;


	AbstractTexture()
	{
		this(null);
	}
	
	AbstractTexture(Rectangle rect)
	{
		if (rect == null)
		{
			rect = SIMPLE_TEXTURE_MAPPING;
		}

		x = rect.x;
		y = rect.y;
		u = rect.width;
		v = rect.height;
	}
	
	protected final void setSizes(float w, float h)
	{
		width = w;
		height = h;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}
	
	

	public abstract void bind();
}
