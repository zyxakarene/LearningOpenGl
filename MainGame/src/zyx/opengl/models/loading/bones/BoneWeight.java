package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class BoneWeight
{	
	float index1;
	float index2;
	float weight1;
	float weight2;
	
	BoneWeight()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		index1 = in.readByte();
		index2 = in.readByte();
		weight1 = in.readFloat();
		weight2 = in.readFloat();
	}
}
