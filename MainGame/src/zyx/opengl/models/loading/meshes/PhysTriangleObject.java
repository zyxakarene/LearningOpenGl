package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;

class PhysTriangleObject
{
	Vector3f v1; 
	Vector3f v2; 
	Vector3f v3; 
	Vector3f normal; 
	
	PhysTriangleObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		float x = in.readFloat();
		float y = in.readFloat();
		float z = in.readFloat();
		v1 = new Vector3f(x, y, z);
		
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		v2 = new Vector3f(x, y, z);
		
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		v3 = new Vector3f(x, y, z);
		
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();		
		normal = new Vector3f(x, y, z);
	}
	
}
