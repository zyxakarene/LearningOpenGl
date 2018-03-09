package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;

public class PhysPlanePicker
{

	public static boolean collided(Vector3f pos, Vector3f dir, PhysBox physbox)
	{
		PhysTriangle[] triangles = physbox.getTriangles();
		boolean collided;

		Vector3f endPos = new Vector3f(pos);
		endPos.x += (dir.x * 10000);
		endPos.y += (dir.y * 10000);
		endPos.z += (dir.z * 10000);
		
		for (PhysTriangle triangle : triangles)
		{
			collided = testTriangle(pos, endPos, triangle);

			if (!collided)
			{
				return false;
			}
		}

		return true;
	}

	private static Vector3f vertex0 = new Vector3f();
	private static Vector3f vertex1 = new Vector3f();
	private static Vector3f vertex2 = new Vector3f();

	private static Vector3f ab = new Vector3f();
	private static Vector3f ac = new Vector3f();
	private static Vector3f triangleNormal = new Vector3f();

	private static boolean testTriangle(Vector3f startPos, Vector3f endPos, PhysTriangle triangle)
	{
		triangle.getVertex1(0, vertex0);
		triangle.getVertex1(1, vertex1);
		triangle.getVertex1(2, vertex2);

		Vector3f.sub(vertex0, vertex1, ab);
		Vector3f.sub(vertex0, vertex2, ac);
		ab.normalise();
		ac.normalise();

		Vector3f.cross(ab, ac, triangleNormal);
		triangleNormal.normalise();

		float dotStart = Vector3f.dot(startPos, triangleNormal);
		float dotEnd = Vector3f.dot(endPos, triangleNormal);
		
		if ((dotStart > 0 && dotEnd < 0) || (dotStart < 0 && dotEnd > 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
