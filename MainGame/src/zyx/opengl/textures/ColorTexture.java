package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.Texture;
import zyx.opengl.GLUtils;
import zyx.opengl.textures.impl.SolidColorTexture;

public class ColorTexture extends AbstractTexture
{

	private static final int BUFFER_ID = 0;

	private SolidColorTexture texture;

	public ColorTexture(int color)
	{
		GL11.glGetError();

		texture = new SolidColorTexture(color);
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
