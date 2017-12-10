package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class SmdBone
{
	byte id;
	String name;
	SmdBone[] children;
	
	float restX;
	float restY;
	float restZ;
	float restRotX;
	float restRotY;
	float restRotZ;
	float restRotW;
	
	SmdBone()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		id = in.readByte();
		name = in.readUTF();
		
		restX = in.readFloat();
		restY = in.readFloat();
		restZ = in.readFloat();
		restRotX = in.readFloat();
		restRotY = in.readFloat();
		restRotZ = in.readFloat();
		restRotW = in.readFloat();
		
		byte childrenCount = in.readByte();
		children = new SmdBone[childrenCount];
		for (int i = 0; i < childrenCount; i++)
		{
			children[i] = new SmdBone();
			children[i].read(in);
		}
	}
}
