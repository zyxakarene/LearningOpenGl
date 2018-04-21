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
import zyx.utils.FloatMath;

public class CheckerdColorTexture implements Texture
{

	private int textureId;
	private final int SIZE = 128;
	private final int BLOCK_SIZE = FloatMath.ceil(128 / 6);

	public CheckerdColorTexture(int colA, int colB)
	{
		TextureImpl.bindNone();

		textureId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		Color colorA = new Color(colA);
		Color colorB = new Color(colB);
		
		float rA = colorA.r;
		float gA = colorA.g;
		float bA = colorA.b;
		
		float rB = colorB.r;
		float gB = colorB.g;
		float bB = colorB.b;
		
		int index = 0;
		float pixels[] = new float[SIZE * SIZE * 3];
		boolean useColorA = true;
		for (int x = 0; x < SIZE; x++)
		{
			for (int y = 0; y < SIZE; y++)
			{
				float percent = (float)y / (float)SIZE * 100f;
				int part = (int) (percent % BLOCK_SIZE);
				if (part == 0)
				{
					useColorA = !useColorA;
				}
				
				if (useColorA)
				{
					pixels[index++] = rA;
					pixels[index++] = gA;
					pixels[index++] = bA;
				}
				else
				{
					pixels[index++] = rB;
					pixels[index++] = gB;
					pixels[index++] = bB;
				}
			}
			
			float percent = (float)x / (float)SIZE * 100f;
			int part = (int) (percent % BLOCK_SIZE);
			if (part == 0)
			{
				useColorA = !useColorA;
			}
		}

		FloatBuffer buffer = BufferUtils.createFloatBuffer(pixels.length);
		buffer.put(pixels);
		buffer.flip();

		GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, SIZE, SIZE, 0, GL_RGB, GL_FLOAT, buffer);
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
	}

	@Override
	public boolean hasAlpha()
	{
		return false;
	}

	@Override
	public int getImageHeight()
	{
		return SIZE;
	}

	@Override
	public int getImageWidth()
	{
		return SIZE;
	}

	@Override
	public float getHeight()
	{
		return SIZE;
	}

	@Override
	public float getWidth()
	{
		return SIZE;
	}

	@Override
	public int getTextureHeight()
	{
		return SIZE;
	}

	@Override
	public int getTextureWidth()
	{
		return SIZE;
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
