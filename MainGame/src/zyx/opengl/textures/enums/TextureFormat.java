package zyx.opengl.textures.enums;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public enum TextureFormat
{
	FORMAT_DEPTH				(0,		GL_DEPTH_COMPONENT32F,	GL_RED,				GL_FLOAT),
	
	FORMAT_1_CHANNEL_UBYTE		(1,		GL_RED,					GL_RED,				GL_UNSIGNED_BYTE),
	FORMAT_2_CHANNEL_UBYTE		(1,		GL_RG,					GL_RG,				GL_UNSIGNED_BYTE),
	FORMAT_3_CHANNEL_UBYTE		(1,		GL_RGB,					GL_RGB,				GL_UNSIGNED_BYTE),
	FORMAT_4_CHANNEL_UBYTE		(1,		GL_RGBA,				GL_RGBA,			GL_UNSIGNED_BYTE),
	
	FORMAT_1_CHANNEL_16F		(1,		GL_R16F,				GL_RED,				GL_FLOAT),
	FORMAT_2_CHANNEL_16F		(1,		GL_RG16F,				GL_RG,				GL_FLOAT),
	FORMAT_3_CHANNEL_16F		(1,		GL_RGB16F,				GL_RGB,				GL_FLOAT),
	FORMAT_4_CHANNEL_16F		(1,		GL_RGBA16F,				GL_RGBA,			GL_FLOAT);

	public static final int COUNT = 5;

	public final int index;
	public final int glInternalFormat;
	public final int glFormat;
	public final int glType;

	private TextureFormat(int index, int glInternalFormat, int glFormat, int glType)
	{
		this.index = index;
		this.glInternalFormat = glInternalFormat;
		this.glFormat = glFormat;
		this.glType = glType;
	}
}
