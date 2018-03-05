package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;

public class PhysTriangle
{
	public PhysVertex v1;
	public PhysVertex v2;
	public PhysVertex v3;

	public void save(DataOutputStream out) throws IOException
	{
		out.writeFloat(v1.x);
		out.writeFloat(v1.y);
		out.writeFloat(v1.z);
		
		out.writeFloat(v2.x);
		out.writeFloat(v2.y);
		out.writeFloat(v2.z);
		
		out.writeFloat(v3.x);
		out.writeFloat(v3.y);
		out.writeFloat(v3.z);
	}

	public void addVertex(PhysVertex vertex)
	{
		if (v1 == null)
		{
			v1 = vertex;
		}
		else if (v2 == null)
		{
			v2 = vertex;
		}
		else if (v3 == null)
		{
			v3 = vertex;
		}
		else
		{
			throw new RuntimeException("Too many verticies for triangle!");
		}
	}

	@Override
	public String toString()
	{
		return "Triangle(" + v1 + v2 + v3 + ")";
	}
}
