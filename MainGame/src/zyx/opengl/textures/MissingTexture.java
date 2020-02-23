package zyx.opengl.textures;

import java.util.HashMap;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.opengl.textures.impl.CheckerdColorTexture;

public class MissingTexture extends AbstractTexture
{
	private static final HashMap<TextureSlot, MissingTexture> CACHE = new HashMap<>();
	
	public static AbstractTexture getAsSlot(TextureSlot slot)
	{
		if (CACHE.containsKey(slot) == false)
		{
			MissingTexture tex = new MissingTexture(slot);
			CACHE.put(slot, tex);
		}
		
		return CACHE.get(slot);
	}
	
	private CheckerdColorTexture texture;
	
	private MissingTexture(TextureSlot slot)
	{
		super("MissingTexture", slot);

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

	@Override
	public ITexture getGlTexture()
	{
		return texture;
	}
}
