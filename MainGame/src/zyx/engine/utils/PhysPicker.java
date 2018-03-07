package zyx.engine.utils;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;

public class PhysPicker
{

	private static final float EPSILON = 0.0000001f;

	public static boolean collided(Vector3f pos, Vector3f dir, PhysBox physbox, Vector3f intersectPoint)
	{
		PhysTriangle[] triangles = physbox.getTriangles();
		boolean collided;

		for (PhysTriangle triangle : triangles)
		{
			collided = testTriangle(pos, dir, triangle, intersectPoint);

			if (collided)
			{
				return true;
			}
		}

		return false;
	}

	private static Vector3f vertex0 = new Vector3f();
	private static Vector3f vertex1 = new Vector3f();
	private static Vector3f vertex2 = new Vector3f();

	private static Vector3f edge1 = new Vector3f();
	private static Vector3f edge2 = new Vector3f();

	private static Vector3f h = new Vector3f();
	private static Vector3f s = new Vector3f();
	private static Vector3f q = new Vector3f();

	private static boolean testTriangle(Vector3f pos, Vector3f dir, PhysTriangle triangle, Vector3f intersectPoint)
	{
		triangle.getVertex1(0, vertex0);
		triangle.getVertex1(1, vertex1);
		triangle.getVertex1(2, vertex2);

		Vector3f.sub(vertex1, vertex0, edge1);
		Vector3f.sub(vertex2, vertex0, edge2);

		Vector3f.cross(dir, edge2, h);
		float a = Vector3f.dot(edge1, h);

		if (a > -EPSILON && a < EPSILON)
		{
			return false;
		}

		float f = 1 / a;

		Vector3f.sub(pos, vertex0, s);
		float u = f * Vector3f.dot(s, h);
		if (u < 0 || u > 1)
		{
			return false;
		}

		Vector3f.cross(s, edge1, q);
		float v = f * Vector3f.dot(dir, q);
		if (v < 0 || u + v > 1)
		{
			return false;
		}

		// At this stage we can compute t to find out where the intersection point is on the line.
		float t = f * Vector3f.dot(edge2, q);
		if (t > EPSILON) // ray intersection
		{
			intersectPoint.set(pos);
			intersectPoint.x += dir.x * t;
			intersectPoint.y += dir.y * t;
			intersectPoint.z += dir.z * t;
			return true;
		}
		else // This means that there is a line intersection but not a ray intersection.
		{
			return false;
		}
	}
}
