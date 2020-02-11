package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class SmdAnimationTransform
{

	String name;
	float x;
	float y;
	float z;
	float rotX;
	float rotY;
	float rotZ;
	float rotW;

	SmdAnimationTransform()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		rotX = in.readFloat();
		rotY = in.readFloat();
		rotZ = in.readFloat();
		rotW = in.readFloat();
	}
}
