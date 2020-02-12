package zyx.opengl.models.loading.meshes;

import java.io.DataInputStream;
import java.io.IOException;

class TriangleObject
{
	VertexObject a;
	VertexObject b;
	VertexObject c;

	TriangleObject()
	{
	}
		
	void read(DataInputStream in) throws IOException
	{
		a = new VertexObject();
		b = new VertexObject();
		c = new VertexObject();
		
		a.read(in);
		b.read(in);
		c.read(in);
	}
}
