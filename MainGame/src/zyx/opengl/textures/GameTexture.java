package zyx.opengl.textures;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import zyx.opengl.GLUtils;
import zyx.utils.geometry.Rectangle;

public class GameTexture extends AbstractTexture
{

	private static final int BUFFER_ID = 0;

	protected Texture texture;

	protected GameTexture(Texture parent, Rectangle rect, String name)
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

		texture = TextureUtils.createTexture(stream);
		setSizes();
	}

	protected void setSizes()
	{
		float w = u - x;
		float h = v - y;

		setSizes(texture.getImageWidth() * w, texture.getImageHeight() * h);
	}

	@Override
	protected void onBind()
	{
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

			GLUtils.errorCheck();
		}
	}
}
