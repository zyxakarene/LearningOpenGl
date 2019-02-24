package zyx.opengl.textures;

import org.newdawn.slick.opengl.TextureImpl;
import zyx.utils.cheats.Print;

public class TextureBinder
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

	public static void unbindTexture()
	{
		TextureImpl.unbind();
		currentlyBoundTexture = null;
	}
	
	static void dispose(AbstractTexture texture)
	{
		if (currentlyBoundTexture == texture)
		{
			TextureImpl.unbind();
			currentlyBoundTexture = null;
		}
	}

	public static String currentTexture()
	{
		if (currentlyBoundTexture != null)
		{
			return currentlyBoundTexture.toString();
		}

		return "[No current texture]";
	}
}
