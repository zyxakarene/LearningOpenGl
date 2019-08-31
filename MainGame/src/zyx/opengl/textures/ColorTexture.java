package zyx.opengl.textures;

import zyx.opengl.textures.enums.TextureSlot;
import zyx.opengl.textures.impl.SolidColorTexture;

public class ColorTexture extends AbstractTexture
{

	private SolidColorTexture texture;

	public ColorTexture(int color)
	{
		this(color, 2, 2, TextureSlot.SHARED_DIFFUSE);
	}
	
	public ColorTexture(int color, TextureSlot slot)
	{
		this(color, 2, 2, slot);
	}
	
	public ColorTexture(int color, float width, float height)
	{
		this(color, width, height, TextureSlot.SHARED_DIFFUSE);
	}
	
	public ColorTexture(int color, float width, float height, TextureSlot slot)
	{
		super("ColorTexture_0x" + Integer.toString(color, 16), slot);
		
		texture = new SolidColorTexture(color);
		setSizes(width, height);
	}

	@Override
	protected void onBind()
	{
		texture.bind();
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}
}
