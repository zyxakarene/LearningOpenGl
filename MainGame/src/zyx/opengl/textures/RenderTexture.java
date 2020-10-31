package zyx.opengl.textures;

import zyx.opengl.buffers.BufferBinder;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.*;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.textures.custom.ITexture;
import zyx.utils.geometry.Rectangle;

public class RenderTexture extends AbstractTexture
{

	private static final Rectangle RENDER_RECT = new Rectangle(0, 0, 0, 0);

	public final int bufferId;
	private final int textureId;
	private final int stencilBufferId;

	public RenderTexture(float width, float height)
	{
		super(RENDER_RECT, "RenderToTexture");
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
	}

	@Override
	protected void onDispose()
	{
		GL30.glDeleteFramebuffers(bufferId);
		GL30.glDeleteRenderbuffers(stencilBufferId);
	}

	private void setupRenderTexture()
	{
		// "Bind" the newly created texture : all future texture functions will modify this texture
		bind();

		BufferBinder.bindBuffer(Buffer.RENDER_TO_TEXTURE);

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

	@Override
	public ITexture getGlTexture()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
