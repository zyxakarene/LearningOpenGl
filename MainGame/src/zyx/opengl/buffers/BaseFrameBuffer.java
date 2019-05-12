package zyx.opengl.buffers;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import zyx.utils.GameConstants;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;

public abstract class BaseFrameBuffer
{
	protected int bufferId;
	protected int depthBufferId;

	protected Buffer buffer;
	
	protected final int w;
	protected final int h;
	
	public BaseFrameBuffer(Buffer bufferEnum, float renderScale)
	{
		this.buffer = bufferEnum;
		
		w = GameConstants.GAME_WIDTH;
		h = GameConstants.GAME_HEIGHT;

		bufferId = GL30.glGenFramebuffers();
		buffer.bufferId = bufferId;
		BufferBinder.bindBuffer(buffer);

		onCreateFrameBufferTextures();

		setupBufferValues();

		BufferBinder.bindBuffer(Buffer.DEFAULT);

		onBufferCreated();
	}

	public void prepareRender()
	{
		BufferBinder.bindBuffer(buffer);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void bindBuffer()
	{
		BufferBinder.bindBuffer(buffer);
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
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, w, h);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBufferId);

		// finally check if framebuffer is complete
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Framebuffer not complete!");
			System.exit(-1);
		}
	}

	protected void onCreateFrameBufferTextures()
	{
	}

	protected void onBufferCreated()
	{
	}

	protected abstract int[] getAttachments();
}
