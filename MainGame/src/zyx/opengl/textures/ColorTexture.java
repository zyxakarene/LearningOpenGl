package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import zyx.opengl.textures.impl.SolidColorTexture;

public class ColorTexture extends AbstractTexture
{

	private static final int BUFFER_ID = 0;

	private SolidColorTexture texture;

	public ColorTexture(int color)
	{
		this(color, 2, 2);
	}
	
	public ColorTexture(int color, float width, float height)
	{
		super("ColorTexture_0x" + Integer.toString(color, 16));
		
		texture = new SolidColorTexture(color);
		setSizes(width, height);
	}

	@Override
	protected void onBind()
	{
//		BufferBinder.bindBuffer(BUFFER_ID);
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
