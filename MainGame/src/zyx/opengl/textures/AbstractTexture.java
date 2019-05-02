package zyx.opengl.textures;

import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractTexture implements IDisposeable
{

	private static final Rectangle SIMPLE_TEXTURE_MAPPING = new Rectangle(0, 0, 1, 1);

	public final float x, y, u, v;
	public final TextureSlot slot;

	private float width;
	private float height;

	protected final String name;

	AbstractTexture(String name)
	{
		this(null, name, TextureSlot.SLOT_0);
	}

	AbstractTexture(String name, TextureSlot slot)
	{
		this(null, name, slot);
	}

	AbstractTexture(Rectangle rect, String name)
	{
		this(rect, name, TextureSlot.SLOT_0);
	}
	AbstractTexture(Rectangle rect, String name, TextureSlot textureSlot)
	{
		if (rect == null)
		{
			rect = SIMPLE_TEXTURE_MAPPING;
		}

		this.name = name;

		x = rect.x;
		y = rect.y;
		u = rect.width;
		v = rect.height;
		
		slot = textureSlot;
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

	public String getName()
	{
		return name;
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

	public final String currentTexture()
	{
		return TextureBinder.currentTexture();
	}
	
	abstract protected void onBind();

	abstract protected void onDispose();

	@Override
	public String toString()
	{
		return String.format("Texture{%s}", name);
	}

}
