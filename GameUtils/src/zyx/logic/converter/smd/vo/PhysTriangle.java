package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;

public class PhysTriangle
{

	public PhysVertex v1;
	public PhysVertex v2;
	public PhysVertex v3;
	public Vector3f normal;

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

		out.writeFloat(normal.x);
		out.writeFloat(normal.y);
		out.writeFloat(normal.z);
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

			normal = new Vector3f();

			Vector3f AB = new Vector3f();
			Vector3f AC = new Vector3f();

			Vector3f vector1 = new Vector3f(v1.x, v1.y, v1.z);
			Vector3f vector2 = new Vector3f(v2.x, v2.y, v2.z);
			Vector3f vector3 = new Vector3f(v3.x, v3.y, v3.z);
			
			Vector3f.sub(vector1, vector2, AB);
			Vector3f.sub(vector1, vector3, AC);
			AB.normalise();
			AC.normalise();

			Vector3f.cross(AB, AC, normal);
			normal.normalise();
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
