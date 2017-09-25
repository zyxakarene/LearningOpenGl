package zyx.opengl.textures;

import org.newdawn.slick.opengl.Texture;
import zyx.utils.interfaces.IDisposeable;

public class GameTexture implements IDisposeable
{

	private static Texture currentlyBoundTexture;

	private Texture texture;

	public GameTexture(String name, String format)
	{
		texture = TextureUtils.createTexture(name, format);
	}

	public void bind()
	{
		if (this != currentlyBoundTexture)
		{
			texture.bind();
			currentlyBoundTexture = texture;
		}
	}

	@Override
	public void dispose()
	{
		if (texture == currentlyBoundTexture)
		{
			currentlyBoundTexture = null;
		}

		if (texture != null)
		{
			texture.release();
			texture = null;
		}
	}
}
