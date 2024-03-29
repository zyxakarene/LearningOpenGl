package zyx.opengl.buffers;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.utils.ScreenSize;

public abstract class BaseFrameBuffer
{

	protected int bufferId;
	protected int depthBufferId;

	protected Buffer buffer;

	protected int w;
	protected int h;
	protected float renderScale;

	public BaseFrameBuffer(Buffer bufferEnum, float renderScale)
	{
		this.buffer = bufferEnum;
		this.renderScale = renderScale;

		resize(ScreenSize.gameWidth, ScreenSize.gameHeight);
	}

	void resize(int width, int height)
	{
		w = (int) (width * renderScale);
		h = (int) (height * renderScale);
	}

	public Vector2f GetSize()
	{
		return new Vector2f(w, h);
	}
	
	void initialize()
	{
		bufferId = GL30.glGenFramebuffers();
		buffer.bufferId = bufferId;
		
		BufferBinder.bindBuffer(buffer);

		onCreateFrameBufferTextures();

		setupBufferValues();

		BufferBinder.bindBuffer(Buffer.DEFAULT);
	}

	public void prepareRender()
	{
		BufferBinder.bindBuffer(buffer);
		GL11.glViewport(0, 0, w, h);
		GL11.glClearColor(1, 1, 1, 0);
		GL11.glClearStencil(0x00);
		
		if (doClearColor)
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		}
		else
		{
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		}
	}

	protected boolean doClearColor = true;
	
	public void bindBuffer()
	{
		BufferBinder.bindBuffer(buffer);
		GL11.glViewport(0, 0, w, h);
	}

	private void setupBufferValues()
	{
		int attachments[] = getAttachments();

		IntBuffer attachmentIntBuffer = BufferUtils.createIntBuffer(attachments.length);
		attachmentIntBuffer.put(attachments);
		attachmentIntBuffer.flip();

		GL20.glDrawBuffers(attachmentIntBuffer);

		depthBufferId = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBufferId);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, w, h);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, depthBufferId); 
		
		// finally check if framebuffer is complete
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.err.println("Framebuffer not complete!");
			System.exit(-1);
		}
	}

	protected void onCreateFrameBufferTextures()
	{
	}

	void onBuffersCreated()
	{
	}

	protected abstract int[] getAttachments();

	void dispose()
	{
		onDispose();

		buffer.bufferId = BufferBinder.UNKNOWN_BUFFER;
		
		GL30.glDeleteFramebuffers(depthBufferId);
		GL30.glDeleteRenderbuffers(bufferId);
	}

	protected abstract void onDispose();
}
