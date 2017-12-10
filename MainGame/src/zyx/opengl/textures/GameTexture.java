package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IDisposeable;

public class GameTexture implements IDisposeable
{
	private static final Rectangle SIMPLE_TEXTURE_MAPPING = new Rectangle(0, 0, 1, 1);
	
	public float x, y, u, v;
	
	public final float width;
	public final float height;
	
	private static Texture currentlyBoundTexture;

	private Texture texture;

	public GameTexture(String path)
	{
		this(path, SIMPLE_TEXTURE_MAPPING);
	}
	
	public GameTexture(String path, Rectangle rect)
	{
		texture = TextureUtils.createTexture(path);
		
		width = texture.getImageWidth();
		height = texture.getImageHeight();
		
		x = rect.x;
		y = rect.y;
		u = rect.width;
		v = rect.height;
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
