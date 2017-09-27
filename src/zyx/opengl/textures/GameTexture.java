package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import zyx.utils.interfaces.IDisposeable;

public class GameTexture implements IDisposeable
{

	public final float width;
	public final float height;
	
	private static Texture currentlyBoundTexture;

	private Texture texture;

	GameTexture(String name, String format)
	{
		texture = TextureUtils.createTexture(name, format);
		
		width = texture.getWidth();
		height = texture.getHeight();
	}

	public void bind()
	{
		if (this != currentlyBoundTexture)
		{
			texture.bind();
			currentlyBoundTexture = texture;
			
			//Swallow some error in Slick-Utils
			//Or maybe I suck at this, who knows!
			GL11.glGetError();
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
