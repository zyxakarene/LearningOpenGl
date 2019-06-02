package zyx.opengl.textures.custom;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL40;
import zyx.opengl.GLUtils;
import zyx.opengl.reflections.LoadableCubemapVO;


public class CubeTexture3D implements ITexture
{

	private int textureId;

	private int width;
	private int height;

	public CubeTexture3D(LoadableCubemapVO cubeVo)
	{
		loadFrom(cubeVo);
	}

	protected void loadFrom(LoadableCubemapVO cubeVo)
	{
		this.textureId = GL11.glGenTextures();
		
		int size = cubeVo.faceSize;
		int layers = cubeVo.layers;
		int faces = 6;
		
		byte[] cubeData = cubeVo.gl_textureData;

		ByteBuffer cubeTextureBuffer = BufferUtils.createByteBuffer(cubeData.length);
		cubeTextureBuffer.put(cubeData);
		cubeTextureBuffer.rewind();
		
		GLUtils.errorCheck();

		int internalFormat = cubeVo.gl_internalFormat;
		int format = cubeVo.gl_format;
		int type = cubeVo.gl_type;
		
		glBindTexture(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, textureId);
		GL12.glTexImage3D(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, 0, internalFormat, size, size, layers * faces, 0, format, type, cubeTextureBuffer);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL40.GL_TEXTURE_CUBE_MAP_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		GLUtils.errorCheck();
		
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
