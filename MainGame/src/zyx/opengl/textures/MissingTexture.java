package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
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
		super("MissingTexture", TextureSlot.SLOT_1);

		texture = new CheckerdColorTexture(0xFF00FF, 0x000000);
		setSizes(texture.getImageWidth(), texture.getImageHeight());
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
	}
}
