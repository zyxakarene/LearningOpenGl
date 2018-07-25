package zyx.opengl.textures;

import org.newdawn.slick.opengl.TextureImpl;

class TextureBinder
{

	private static AbstractTexture currentlyBoundTexture;

	static void bind(AbstractTexture texture)
	{
		if (currentlyBoundTexture != texture)
		{
			currentlyBoundTexture = texture;
			currentlyBoundTexture.onBind();
		}
	}

	static void dispose(AbstractTexture texture)
	{
		if (currentlyBoundTexture == texture)
		{
			TextureImpl.unbind();
			currentlyBoundTexture = null;
		}
	}
}
