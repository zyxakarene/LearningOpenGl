package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.WorldShader;

public class PhysTriangle
{
	final PhysVertex v1;
	final PhysVertex v2;
	final PhysVertex v3;

	public PhysTriangle(PhysVertex v1, PhysVertex v2, PhysVertex v3)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	public void getVertex1(int index, Vector3f out)
	{
		PhysVertex vertex = null;
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
