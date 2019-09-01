package zyx.opengl.textures.impl;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import zyx.opengl.textures.TextureBinder;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureFormat;
import zyx.utils.cheats.Print;

public class CustomDataTexture implements ITexture
{

	private int textureId;

	private float[] pixels;
	private FloatBuffer buffer;

	private final int textureW;
	private final int textureH;

	public CustomDataTexture(int width, int height, float[] pixelData)
	{
		textureW = width;
		textureH = height;
		pixels = pixelData;

		TextureBinder.unbindTextures();

		textureId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		buffer = BufferUtils.createFloatBuffer(pixelData.length);

		updateTextureFromData();
	}

	public final void updateTextureFromData()
	{
		buffer.rewind();
		buffer.put(pixels);
		buffer.flip();

		TextureFormat format = TextureFormat.FORMAT_4_CHANNEL_16F;
		GL11.glTexImage2D(GL_TEXTURE_2D, 0, format.glInternalFormat, textureW, textureH, 0, format.glFormat, format.glType, buffer);
	}

	@Override
	public void bind()
	{
		GL11.glBindTexture(GL_TEXTURE_2D, textureId);
	}

	@Override
	public void dispose()
	{
		GL11.glDeleteTextures(textureId);
		textureId = -1;
	}

	@Override
	public int getHeight()
	{
		return textureH;
	}

	@Override
	public int getWidth()
	{
		return textureW;
	}
}
