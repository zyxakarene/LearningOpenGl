package zyx.opengl.textures.impl;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import org.newdawn.slick.Color;
import zyx.opengl.textures.TextureBinder;
import zyx.opengl.textures.custom.ITexture;

public class SolidColorTexture implements ITexture
{

	private int textureId;

	public SolidColorTexture(int color)
	{
		TextureBinder.unbindTextures();
		
		textureId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		Color colorObj = new Color(color);
		
		float r = colorObj.r;
		float g = colorObj.g;
		float b = colorObj.b;

		// Black/white checkerboard
		float pixels[] =
		{
			r, g, b, r, g, b,
			r, g, b, r, g, b
		};

		FloatBuffer buffer = BufferUtils.createFloatBuffer(pixels.length);
		buffer.put(pixels);
		buffer.flip();

		GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 2, 2, 0, GL_RGB, GL_FLOAT, buffer);
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
		return 2;
	}

	@Override
	public int getWidth()
	{
		return 2;
	}
}
