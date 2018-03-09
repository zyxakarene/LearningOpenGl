package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;

public class PhysTriangle
{
	final Vector3f v1;
	final Vector3f v2;
	final Vector3f v3;

	public PhysTriangle(Vector3f v1, Vector3f v2, Vector3f v3)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	public void getVertex1(int index, Vector3f out)
	{
		Vector3f vertex = null;
		switch(index)
		{
			case(0):{vertex = v1; break;}
			case(1):{vertex = v2; break;}
			case(2):{vertex = v3; break;}
		}
		
		out.x = vertex.x;
		out.y = vertex.y;
		out.z = vertex.z;
	}
	
	
}
