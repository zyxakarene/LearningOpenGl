package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.PrintBuilder;

class ZafObject
{
	private static final int POSITION_DATA = 3;
	private static final int NORMAL_DATA = 3;
	private static final int UV_DATA = 2;
	private static final int BONE_DATA = 2;
	
	int boneCount;
	float[] vertexData;
	int[] elementData;
	PhysObject physInformation;
	String diffuseTexture;
	String normalTexture;
	String specularTexture;
	
	Vector3f radiusCenter;
	float radius;
	
	String skeletonId;
	
	public void read(DataInputStream in, PrintBuilder builder) throws IOException
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
				
		physInformation = new PhysObject();
		physInformation.read(in);
		
		builder.append("↳", physInformation.physBoxes.length, "physboxes");
		
		diffuseTexture = in.readUTF();
		normalTexture = in.readUTF();
		specularTexture = in.readUTF();
		builder.append("↳", "Textures:", diffuseTexture, normalTexture, specularTexture);
		
		radiusCenter = new Vector3f();
		radiusCenter.x = in.readFloat();
		radiusCenter.y = in.readFloat();
		radiusCenter.z = in.readFloat();
		radius = in.readFloat();
		
		skeletonId = in.readUTF();
		builder.append("↳", "Skeleton:", skeletonId);
	}
}
