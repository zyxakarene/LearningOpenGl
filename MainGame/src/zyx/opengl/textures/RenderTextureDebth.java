package zyx.opengl.textures;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.TextureImpl;
import zyx.utils.geometry.Rectangle;

public class RenderTextureDebth extends AbstractTexture
{

	private static final Rectangle RENDER_RECT = new Rectangle(0, 0, -1, 1);
	
	public final int bufferId;
	private final int textureId;
	private final int stencilBufferId;

	public RenderTextureDebth(float width, float height)
	{
		super(RENDER_RECT, "RenderToTextureDebth");
		bufferId = GL30.glGenFramebuffers();

		textureId = GL11.glGenTextures();
		
		stencilBufferId = GL30.glGenRenderbuffers();


		setSizes(width, height);
		setupRenderTexture();
	}

	@Override
	protected void onBind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, stencilBufferId);
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
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, w, h, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_INT, (ByteBuffer) null);

		// Poor filtering. Needed !
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, textureId, 0);
		GL11.glDrawBuffer(GL11.GL_NONE);
		GL11.glReadBuffer(GL11.GL_NONE);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}
}
