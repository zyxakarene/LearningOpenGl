package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class AnimationTransformObject
{

	byte boneId;
	float x;
	float y;
	float z;
	float rotX;
	float rotY;
	float rotZ;
	float rotW;

	AnimationTransformObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		boneId = in.readByte();
		
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		rotX = in.readFloat();
		rotY = in.readFloat();
		rotZ = in.readFloat();
		rotW = in.readFloat();
	}
}
