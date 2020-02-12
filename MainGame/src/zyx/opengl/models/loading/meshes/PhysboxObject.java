package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;

class PhysboxObject
{
	PhysTriangleObject[] triangles;
	short boneId;
	
	PhysboxObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		boneId = in.readShort();
		
		int length = in.readInt();
		triangles = new PhysTriangleObject[length];
		
		for (int i = 0; i < length; i++)
		{
			triangles[i] = new PhysTriangleObject();
			triangles[i].read(in);
		}
	}
}
