package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class SmdTriangle
{
	SmdVertex a;
	SmdVertex b;
	SmdVertex c;

	SmdTriangle()
	{
	}
		
	void read(DataInputStream in) throws IOException
	{
		a = new SmdVertex();
		b = new SmdVertex();
		c = new SmdVertex();
		
		a.read(in);
		b.read(in);
		c.read(in);
	}
}
