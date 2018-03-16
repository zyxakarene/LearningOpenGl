package zyx.opengl.models.loading;

import java.io.DataInputStream;
import java.io.IOException;

class SmdPhysbox
{
	SmdPhysTriangle[] triangles;
	short boneId;
	
	SmdPhysbox()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		boneId = in.readShort();
		
		int length = in.readInt();
		triangles = new SmdPhysTriangle[length];
		
		for (int i = 0; i < length; i++)
		{
			triangles[i] = new SmdPhysTriangle();
			triangles[i].read(in);
		}
	}
}
