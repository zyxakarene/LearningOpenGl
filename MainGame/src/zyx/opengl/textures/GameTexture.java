package zyx.opengl.textures;

import java.io.InputStream;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.custom.Texture;
import zyx.opengl.textures.enums.TextureFiltering;
import zyx.utils.geometry.Rectangle;

public class GameTexture extends AbstractTexture
{

	protected ITexture texture;

	protected GameTexture(ITexture parent, Rectangle rect, String name)
	{
		super(rect, name);

		texture = parent;
		
		setSizes();
	}

	public GameTexture(InputStream stream, String name)
	{
		this(stream, null, name);
	}

	public GameTexture(InputStream stream, Rectangle rect, String name)
	{
		super(rect, name);

		texture = new Texture(stream, TextureFiltering.NEAREST);
		setSizes();
	}

	protected void setSizes()
	{
		float w = u - x;
		float h = v - y;

		setSizes(texture.getWidth()* w, texture.getHeight()* h);
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
