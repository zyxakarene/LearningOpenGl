package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.SGL;

public class TextureFromInt extends AbstractTexture
{

	private final int textureId;

	public TextureFromInt(int id)
	{
		super("TextureFromId:" + id);

		this.textureId = id + 0;
		
		setSizes(512, 512);
	}

	@Override
	protected void onBind()
	{
		TextureImpl.bindNone();
		
		GL11.glEnable(SGL.GL_TEXTURE_2D);
		GL11.glBindTexture(GL_TEXTURE_2D, textureId);

		//Swallow some error in Slick-Utils
		//Or maybe I suck at this, who knows!
		GL11.glGetError();
	}

	@Override
	protected void onDispose()
	{
	}

}
