package zyx.opengl.models.loading.cubemaps;

import java.io.IOException;
import java.util.Arrays;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.utils.PrintBuilder;

class CubemapObject
{
	public String name;
	
	public short faceSize;
	public short layers;
	
	public int internalFormat;
	public int format;
	public int type;
	
	public byte[] textureData;
	public Vector3f[] positions;

	CubemapObject()
	{
	}
	
	void read(ResourceDataInputStream in, PrintBuilder builder) throws IOException
	{
		name = in.readUTF();
		builder.append("↳ Name", name);
		
		faceSize = in.readShort();
		builder.append("↳", faceSize, "px face size");
		layers = in.readShort();
		builder.append("↳", layers, "positions");

		
		internalFormat = in.readInt();
		format = in.readInt();
		type = in.readInt();
		builder.append("↳ GL formats", internalFormat, format, type);
		
		int textureLength = in.readInt();
		builder.append("↳", textureLength, "bytes of texture");
		textureData = new byte[textureLength];
		in.readFully(textureData);
		
		positions = new Vector3f[layers];
		for (int i = 0; i < layers; i++)
		{
			float x = in.readFloat();
			float y = in.readFloat();
			float z = in.readFloat();
			
			positions[i] = new Vector3f(x, y, z);
		}
		builder.append("↳ Positions", Arrays.toString(positions));
	}

}
