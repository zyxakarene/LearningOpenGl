package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.PrintBuilder;

class SubMeshObject
{
	private static final int POSITION_DATA = 3;
	private static final int NORMAL_DATA = 3;
	private static final int UV_DATA = 2;
	private static final int BONE_DATA = 2;
	
	int boneCount;
	float[] vertexData;
	int[] elementData;
	String diffuseTexture;
	String normalTexture;
	String specularTexture;
	MaterialObject materialInformation;
	
	void read(DataInputStream in, PrintBuilder builder) throws IOException
	{
		boneCount = in.readByte();
		
		int vertexCount = in.readInt();
		int floatCount = vertexCount * (POSITION_DATA + NORMAL_DATA + UV_DATA + (BONE_DATA * boneCount));
		vertexData = new float[floatCount];
		builder.append("↳", vertexCount, "verticies");
		builder.append("↳", vertexData.length, "floats");
		for (int i = 0; i < vertexData.length; i++)
		{
			vertexData[i] = in.readFloat();
		}
		
		int elementCount = in.readInt();
		elementData = new int[elementCount];
		builder.append("↳", elementData.length, "elements");
		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = in.readShort();
		}
						
		diffuseTexture = in.readUTF();
		normalTexture = in.readUTF();
		specularTexture = in.readUTF();
		builder.append("↳", "Textures:", diffuseTexture, normalTexture, specularTexture);
		
		materialInformation = new MaterialObject();
		materialInformation.read(in);
	}

}
