package zyx.opengl.textures;

import org.newdawn.slick.opengl.Texture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractTexture implements IDisposeable
{

	private static final Rectangle SIMPLE_TEXTURE_MAPPING = new Rectangle(0, 0, 1, 1);

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

	public final void bind()
	{
		TextureBinder.bind(this);
	}
	
	@Override
	public final void dispose()
	{
		TextureBinder.dispose(this);
		
		onDispose();
	}

	abstract protected void onBind();
	abstract protected void onDispose();
}
