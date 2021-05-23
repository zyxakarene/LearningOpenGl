package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.PrintBuilder;

class ZafObject
{
	SubMeshObject[] subMeshes;
	
	Vector3f radiusCenter;
	float radius;
	
	String skeletonId;
	
	public void read(DataInputStream in, PrintBuilder builder) throws IOException
	{
		int subMeshCount = in.readByte();
		subMeshes = new SubMeshObject[subMeshCount];
		
		builder.append("↳", "Submeshes:", subMeshCount);
		
		for (int i = 0; i < subMeshCount; i++)
		{
			builder.append("↳", "Submesh", i);
			SubMeshObject sub = new SubMeshObject();
			sub.read(in, builder);
			
			subMeshes[i] = sub;
		}
		
		radiusCenter = new Vector3f();
		radiusCenter.x = in.readFloat();
		radiusCenter.y = in.readFloat();
		radiusCenter.z = in.readFloat();
		radius = in.readFloat();
		
		skeletonId = in.readUTF();
		builder.append("↳", "Skeleton:", skeletonId);
	}
}
