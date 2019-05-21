package zyx.opengl.textures;

import zyx.opengl.textures.impl.SolidColorTexture;

public class ColorTexture extends AbstractTexture
{

	private SolidColorTexture texture;

	public ColorTexture(int color)
	{
		this(color, 2, 2);
	}
	
	public ColorTexture(int color, float width, float height)
	{
		super("ColorTexture_0x" + Integer.toString(color, 16));
		
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
