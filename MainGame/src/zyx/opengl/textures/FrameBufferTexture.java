package zyx.opengl.textures;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import zyx.opengl.textures.enums.TextureAttachment;
import zyx.opengl.textures.enums.TextureFormat;

public class FrameBufferTexture
{

	private static final ByteBuffer NULL_BUFFER = null;

	public final int id;
	public final int attachment;

	public FrameBufferTexture(int width, int height, TextureAttachment attachment, TextureFormat format)
	{
		this.id = glGenTextures();
		this.attachment = attachment.glAttachment;

		glBindTexture(GL_TEXTURE_2D, id);
		glTexImage2D(GL_TEXTURE_2D, 0, format.glInternalFormat, width, height, 0, format.glFormat, format.glType, NULL_BUFFER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

		glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.glAttachment, GL_TEXTURE_2D, id, 0);
	}

	public void dispose()
	{
		GL11.glDeleteTextures(id);
	}

}
