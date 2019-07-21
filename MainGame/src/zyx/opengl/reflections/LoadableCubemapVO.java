package zyx.opengl.reflections;

import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

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
	
	
	LoadableCubemapVO()
	{
	}
	
	void read(ResourceDataInputStream in) throws IOException
	{
		name = in.readUTF();
		
		faceSize = in.readShort();
		layers = in.readShort();

		
		gl_internalFormat = in.readInt();
		gl_format = in.readInt();
		gl_type = in.readInt();
		
		int textureLength = in.readInt();
		gl_textureData = new byte[textureLength];
		in.read(gl_textureData);
		
		positions = new Vector3f[layers];
		for (int i = 0; i < layers; i++)
		{
			float x = in.readFloat();
			float y = in.readFloat();
			float z = in.readFloat();
			
			positions[i] = new Vector3f(x, y, z);
		}
	}

}
