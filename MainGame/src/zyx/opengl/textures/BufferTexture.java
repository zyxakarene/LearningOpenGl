package zyx.opengl.textures;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.GL_RGB16F;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.enums.TextureAttachment;

class BufferTexture
{

	private int bufferId;

	public int position;
	public int normal;
	public int color;

	private FrameBufferTexture positionTex;
	private FrameBufferTexture normalTex;
	private FrameBufferTexture colorTex;
	
	private BufferTexture(int width, int height)
	{
		ShaderManager.INSTANCE.bind(Shader.WORLD);
		
		bufferId = GL30.glGenFramebuffers();
		BufferBinder.bindBuffer(bufferId);

		positionTex = new FrameBufferTexture(width, height, TextureAttachment.ATTACHMENT_0);
		position = positionTex.id;

		normalTex = new FrameBufferTexture(width, height, TextureAttachment.ATTACHMENT_1);
		normal = normalTex.id;

		colorTex = new FrameBufferTexture(width, height, TextureAttachment.ATTACHMENT_2);
		color = colorTex.id;
		
		int attachments[] =
		{
			GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2
		};

		IntBuffer buffer = BufferUtils.createIntBuffer(attachments.length);
		buffer.put(attachments);
		buffer.flip();

		GL20.glDrawBuffers(buffer);

		int rboDepth = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rboDepth);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboDepth);
		
		// finally check if framebuffer is complete
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Framebuffer not complete!");
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void bind()
	{
		BufferBinder.bindBuffer(bufferId);
	}

	public void dispose()
	{
		GL11.glDeleteTextures(position);
		GL11.glDeleteTextures(normal);
		GL11.glDeleteTextures(color);

		GL30.glDeleteFramebuffers(bufferId);
	}
}
