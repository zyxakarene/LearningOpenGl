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
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import zyx.opengl.textures.TextureBinder;

public class SolidColorTexture implements Texture
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
		TextureImpl.bindNone();
		GL11.glBindTexture(GL_TEXTURE_2D, textureId);
	}

	@Override
	public void release()
	{
		GL11.glDeleteTextures(textureId);
		textureId = 0;
	}

	@Override
	public boolean hasAlpha()
	{
		return false;
	}

	@Override
	public int getImageHeight()
	{
		return 2;
	}

	@Override
	public int getImageWidth()
	{
		return 2;
	}

	@Override
	public float getHeight()
	{
		return 2;
	}

	@Override
	public float getWidth()
	{
		return 2;
	}

	@Override
	public int getTextureHeight()
	{
		return 2;
	}

	@Override
	public int getTextureWidth()
	{
		return 2;
	}

	@Override
	public int getTextureID()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte[] getTextureData()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getTextureRef()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
