package zyx.logic.converter.smd.vo.validator;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.vo.PhysBox;
import zyx.logic.converter.smd.vo.PhysTriangle;
import zyx.logic.converter.smd.vo.PhysVertex;

public class PhysBoxValidator
{

	private ArrayList<PhysTriangle> verticies;
	private boolean valid;
	private float BIAS = 0.001f;

	public PhysBoxValidator(PhysBox box)
	{
		this.verticies = box.triangles;
		this.valid = true;
	}

	public boolean validate()
	{
		Vector3f triangleNormal = new Vector3f();
		for (PhysTriangle triangle : verticies)
		{
			Vector3f ab = toVector(triangle.v1, triangle.v2);
			Vector3f ac = toVector(triangle.v1, triangle.v3);

			Vector3f.cross(ab, ac, triangleNormal);
			triangleNormal.normalise();

			testNormal(triangleNormal, triangle.v1);
			
			if (!valid)
			{
				break;
			}
		}
		
		return valid;
	}

	private void testNormal(Vector3f normal, PhysVertex planeVertex)
	{
		for (PhysTriangle triangle : verticies)
		{
			testCombo(normal, triangle.v1, planeVertex);
			testCombo(normal, triangle.v2, planeVertex);
			testCombo(normal, triangle.v3, planeVertex);
		}
	}

	private void testCombo(Vector3f normal, PhysVertex vertex, PhysVertex planeVertex)
	{
		if (vertex != planeVertex)
		{
			Vector3f vec = toVector(vertex, planeVertex);
			if (vec != null)
			{
				float dot = Vector3f.dot(vec, normal);
				System.out.println(dot);
				if (dot > BIAS)
				{
					valid = false;
				}
			}
		}
	}

	private Vector3f toVector(PhysVertex v1, PhysVertex v2)
	{
		Vector3f vec = new Vector3f();
		vec.x = v1.x - v2.x;
		vec.y = v1.y - v2.y;
		vec.z = v1.z - v2.z;
		if (vec.length() == 0)
		{
			return null;
		}
		else
		{
			vec.normalise();
			return vec;
		}
	}
}
