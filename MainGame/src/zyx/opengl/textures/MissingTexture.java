package zyx.opengl.textures;

import zyx.opengl.textures.enums.TextureSlot;
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

	private CheckerdColorTexture texture;

	private MissingTexture()
	{
		super("MissingTexture", TextureSlot.SLOT_1);

		texture = new CheckerdColorTexture(0xFF00FF, 0x000000);
		setSizes(texture.getWidth(), texture.getHeight());
	}

	@Override
	protected void onBind()
	{
		texture.bind();
	}

	@Override
	protected void onDispose()
	{
		//Do not dispose this forever kept texture. It is eternal and must not be destroyed
	}
}
