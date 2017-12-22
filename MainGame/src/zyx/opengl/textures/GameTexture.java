package zyx.opengl.textures;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import zyx.utils.geometry.Rectangle;

public class GameTexture extends AbstractTexture
{

	private static final int BUFFER_ID = 0;
	
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
	protected void onBind()
	{
		BufferBinder.bindBuffer(BUFFER_ID);
		glActiveTexture(GL13.GL_TEXTURE0);
		texture.bind();

		//Swallow some error in Slick-Utils
		//Or maybe I suck at this, who knows!
		GL11.glGetError();
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
		{
			texture.release();
			texture = null;
		}
	}
}
