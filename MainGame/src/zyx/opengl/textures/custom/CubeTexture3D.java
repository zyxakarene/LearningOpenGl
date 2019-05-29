package zyx.opengl.textures.custom;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
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
import org.lwjgl.opengl.GL40;
import zyx.a_wip.CubemapThings;
import zyx.opengl.GLUtils;
import zyx.utils.cheats.Print;


public class CubeTexture3D implements ITexture
{

	private int textureId;

	private int width;
	private int height;

	public CubeTexture3D(InputStream content)
	{
		try
		{
			loadFrom(content);
		}
		catch (IOException ex)
		{
			Print.err(ex.getMessage());
			throw new RuntimeException("Could not create 3D Cubemap texture", ex);
		}
	}

	protected void loadFrom(InputStream content) throws IOException
	{
		this.textureId = GL11.glGenTextures();
		
		int size = 128;
		int layers = 1;
		int faces = 6;
		int pixelCount = size * size * layers * faces;
		
		byte[] cubeData = new byte[pixelCount * 3];
		int readCount = content.read(cubeData);

		ByteBuffer cubeTextureBuffer = BufferUtils.createByteBuffer(pixelCount * 3);
		cubeTextureBuffer.put(cubeData);
		cubeTextureBuffer.rewind();
//		cubeTextureBuffer.flip();
		
//		cubeTextureBuffer = CubemapThings.GetNoiseData(pixelCount);
		
		GLUtils.errorCheck();

		glBindTexture(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, textureId);
		GL12.glTexImage3D(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, 0, GL_RGB, size, size, layers * faces, 0, GL_RGB, GL11.GL_BYTE, cubeTextureBuffer);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		this.width = size;
		this.height = size;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void bind()
	{
		glBindTexture(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, textureId);
	}

	@Override
	public void dispose()
	{
		GL11.glDeleteTextures(textureId);
		textureId = -1;
	}

}
