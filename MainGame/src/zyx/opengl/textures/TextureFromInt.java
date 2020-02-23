package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureSlot;

public class TextureFromInt extends AbstractTexture
{

	private final int textureId;
	
	public TextureFromInt(int width, int height, int id, TextureSlot slot)
	{
		super("TextureFromId:" + id, slot);

		this.textureId = id + 0;
		
		setSizes(width, height);
	}

	@Override
	protected void onBind()
	{
		GL11.glBindTexture(GL_TEXTURE_2D, textureId);
	}

	@Override
	protected void onDispose()
	{
	}

	@Override
	public ITexture getGlTexture()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
