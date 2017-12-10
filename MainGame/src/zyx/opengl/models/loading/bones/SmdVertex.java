package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class SmdVertex
{

	float x;
	float y;
	float z;
	float normX;
	float normY;
	float normZ;
	float u;
	float v;
	BoneWeight weights;
	
	SmdVertex()
	{
		weights = new BoneWeight();
	}
	
	void read(DataInputStream in) throws IOException
	{
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		normX = in.readFloat();
		normY = in.readFloat();
		normZ = in.readFloat();
		u = in.readFloat();
		v = in.readFloat();
		
		weights.read(in);
	}
	
	
}
