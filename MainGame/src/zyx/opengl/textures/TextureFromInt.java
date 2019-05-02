package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import org.lwjgl.opengl.GL13;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.SGL;
import zyx.opengl.GLUtils;
import zyx.utils.GameConstants;

public class TextureFromInt extends AbstractTexture
{

	private final int textureId;
	private final int attachment;

	public TextureFromInt(int id, int attachment)
	{
		super("TextureFromId:" + id);

		this.textureId = id + 0;
		this.attachment = attachment;
		
		setSizes(512, 512);
	}

	@Override
	protected void onBind()
	{
		TextureImpl.bindNone();
		
		GL11.glEnable(SGL.GL_TEXTURE_2D);
		glActiveTexture(attachment);
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
