package zyx.opengl.textures;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.newdawn.slick.opengl.TextureImpl;

public class RenderTexture extends AbstractTexture
{

	public final int bufferId;
	private final int textureId;
	private final int stencilBufferId;

	public RenderTexture(float width, float height)
	{
		bufferId = GL30.glGenFramebuffers();

		textureId = GL11.glGenTextures();
		
		stencilBufferId = GL30.glGenRenderbuffers();


		setSizes(width, height);
		setupRenderTexture();
		
		int type = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
		if(type != GL30.GL_FRAMEBUFFER_COMPLETE)
		{
			System.err.println("Type:" + type);
		}
	}

	@Override
	protected void onBind()
	{
		glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		TextureImpl.unbind();
	}

	@Override
	protected void onDispose()
	{
		GL30.glDeleteFramebuffers(bufferId);
	}

	private void setupRenderTexture()
	{
		// "Bind" the newly created texture : all future texture functions will modify this texture
		bind();

		BufferBinder.bindBuffer(bufferId);

		// Give an empty image to OpenGL ( the last "0" )
		int w = (int) getWidth();
		int h = (int) getHeight();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, w, h, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		// Poor filtering. Needed !
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, textureId, 0);
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, stencilBufferId);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, w, h);

		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, stencilBufferId);
	}
}
