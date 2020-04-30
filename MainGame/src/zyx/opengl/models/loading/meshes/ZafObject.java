package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.PrintBuilder;

class ZafObject
{
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
		int vertexCount = in.readInt();
		vertexData = new float[vertexCount * 16];
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
