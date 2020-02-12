package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.geometry.Box;

class PhysObject
{
	PhysboxObject[] physBoxes;
	Box boundingBox;
	
	PhysObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		Vector3f min = new Vector3f();
		min.x = in.readFloat();
		min.y = in.readFloat();
		min.z = in.readFloat();
		
		Vector3f max = new Vector3f();
		max.x = in.readFloat();
		max.y = in.readFloat();
		max.z = in.readFloat();
		
		boundingBox = new Box(min.x, max.x, min.y, max.y, min.z, max.z);
		
		int length = in.readInt();
		physBoxes = new PhysboxObject[length];
		for (int i = 0; i < length; i++)
		{
			physBoxes[i] = new PhysboxObject();
			physBoxes[i].read(in);
		}
	}
}
