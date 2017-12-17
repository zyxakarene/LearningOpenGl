package zyx.opengl.textures;

import java.io.InputStream;
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

	public GameTexture(InputStream stream)
	{
		this(stream, SIMPLE_TEXTURE_MAPPING);
	}
	
	public GameTexture(InputStream stream, Rectangle rect)
	{
		if (rect == null)
		{
			rect = SIMPLE_TEXTURE_MAPPING;
		}
		
		texture = TextureUtils.createTexture(stream);
		
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
