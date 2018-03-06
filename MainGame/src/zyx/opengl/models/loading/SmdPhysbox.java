package zyx.opengl.models.loading;

import java.io.DataInputStream;
import java.io.IOException;

class SmdPhysbox
{
	SmdPhysTriangle[] triangles;
	
	SmdPhysbox()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		int length = in.readInt();
		triangles = new SmdPhysTriangle[length];
		
		for (int i = 0; i < length; i++)
		{
			triangles[i] = new SmdPhysTriangle();
			triangles[i].read(in);
		}
	}
}
