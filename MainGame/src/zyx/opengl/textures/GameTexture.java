package zyx.opengl.textures;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import zyx.utils.geometry.Rectangle;

public class GameTexture extends AbstractTexture
{
	
	private Texture texture;

	public GameTexture(InputStream stream)
	{
		this(stream, null);
	}
	
	public GameTexture(InputStream stream, Rectangle rect)
	{
		super(rect);
		
		texture = TextureUtils.createTexture(stream);
		
		setSizes(texture.getImageWidth(), texture.getImageHeight());
	}

	@Override
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
