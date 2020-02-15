package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class BoneObject
{
	byte id;
	BoneObject[] children;
	
	float restX;
	float restY;
	float restZ;
	float restRotX;
	float restRotY;
	float restRotZ;
	float restRotW;
	
	BoneObject()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		id = in.readByte();
		
		restX = in.readFloat();
		restY = in.readFloat();
		restZ = in.readFloat();
		restRotX = in.readFloat();
		restRotY = in.readFloat();
		restRotZ = in.readFloat();
		restRotW = in.readFloat();
		
		byte childrenCount = in.readByte();
		children = new BoneObject[childrenCount];
		for (int i = 0; i < childrenCount; i++)
		{
			children[i] = new BoneObject();
			children[i].read(in);
		}
	}
}
