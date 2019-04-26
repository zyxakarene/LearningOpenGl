package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import zyx.opengl.textures.impl.CheckerdColorTexture;

public class MissingTexture extends AbstractTexture
{

	private static MissingTexture instance;

	public static MissingTexture getInstance()
	{
		if (instance == null)
		{
			instance = new MissingTexture();
		}
		
		return instance;
	}
	
	private static final int BUFFER_ID = 0;

	private CheckerdColorTexture texture;

	private MissingTexture()
	{
		super("MissingTexture");

		texture = new CheckerdColorTexture(0xFF00FF, 0x000000);
		setSizes(texture.getImageWidth(), texture.getImageHeight());
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
	}
}
