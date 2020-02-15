package zyx.opengl.reflections;

import org.lwjgl.util.vector.Vector3f;

public class LoadableCubemapVO
{
	
	public String name;
	
	public short faceSize;
	public short layers;
	
	public int gl_internalFormat;
	public int gl_format;
	public int gl_type;
	public byte[] gl_textureData;
	public Vector3f[] positions;

	public LoadableCubemapVO(String name, short faceSize, short layers, int gl_internalFormat, int gl_format, int gl_type, byte[] gl_textureData, Vector3f[] positions)
	{
		this.name = name;
		this.faceSize = faceSize;
		this.layers = layers;
		this.gl_internalFormat = gl_internalFormat;
		this.gl_format = gl_format;
		this.gl_type = gl_type;
		this.gl_textureData = gl_textureData;
		this.positions = positions;
	}

	public void dispose()
	{
		name = null;
		gl_textureData = null;
		positions = null;
	}
}
