package zyx.a_wip;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;
import zyx.opengl.GLUtils;

public class CubemapThings
{
	private void createCubemapTexture()
	{
		int size = 128;
		int layers = 4;
		int faces = 6;
		int pixelCount = size * size * layers * faces;
		
		Random random = new Random();
		float[] noiseData = new float[pixelCount * 3];
		for (int i = 0; i < noiseData.length; i += 3)
		{
			float valueX = random.nextFloat() * 2f - 1;
			float valueY = random.nextFloat() * 2f - 1;
			float valueZ = random.nextFloat() * 2f - 1;

			noiseData[i + 0] = valueX;
			noiseData[i + 1] = valueY;
			noiseData[i + 2] = valueZ;
		}

		FloatBuffer noiseTextureBuffer = BufferUtils.createFloatBuffer(pixelCount * 3);
		noiseTextureBuffer.put(noiseData);
		noiseTextureBuffer.flip();
		
		GLUtils.errorCheck();
		int cubeArrayTexture = GL11.glGenTextures();
		glBindTexture(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, cubeArrayTexture);
		GL12.glTexImage3D(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, 0, GL30.GL_RGB16F, size, size, layers * faces, 0, GL_RGB, GL_FLOAT, noiseTextureBuffer);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		/*
			GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516,
			GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518,
			GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A,
		*/
	}
	
	public static ByteBuffer GetNoiseData(int pixelCount)
	{
		Random random = new Random();
		byte[] noiseData = new byte[pixelCount * 3];
		int loopCounter = 0;
		byte colR = (byte) 0;
		byte colG = (byte) 0;
		byte colB = (byte) 0;
		for (int i = 0; i < noiseData.length; i += 3)
		{
			switch (loopCounter)
			{
				case 128 * 128 * 0:
					//Face 1 - Tail
					colR = Byte.MAX_VALUE;
					colG = 0;
					colB = 0;
					break;
				case 128 * 128 * 1:
					//Face 1 - Face
					colR = 0;
					colG = Byte.MAX_VALUE;
					colB = 0;
					break;
				case 128 * 128 * 2:
					//Face 1 - Backward bend
					colR = 0;
					colG = 0;
					colB = Byte.MAX_VALUE;
					break;
				case 128 * 128 * 3:
					//Face 1 - Forward bend
					colR = Byte.MAX_VALUE;
					colG = Byte.MAX_VALUE;
					colB = 0;
					break;
				case 128 * 128 * 4:
					//Face 1 - Up
					colR = Byte.MAX_VALUE;
					colG = 0;
					colB = Byte.MAX_VALUE;
					break;
				case 128 * 128 * 5:
					//Face 1 - down
					colR = 0;
					colG = Byte.MAX_VALUE;
					colB = Byte.MAX_VALUE;
					break;
			}

			noiseData[i + 0] = colR;
			noiseData[i + 1] = colG;
			noiseData[i + 2] = colB;
			
			loopCounter++;
			
		}

		ByteBuffer noiseTextureBuffer = BufferUtils.createByteBuffer(pixelCount * 3);
		noiseTextureBuffer.put(noiseData);
		noiseTextureBuffer.flip();
		
		return noiseTextureBuffer;
	}
}
