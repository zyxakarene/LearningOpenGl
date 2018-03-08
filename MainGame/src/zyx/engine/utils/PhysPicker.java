package zyx.engine.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.interfaces.IPhysbox;

public class PhysPicker
{

	private static final float EPSILON = 0.0000001f;

	public static boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		if (phys == null)
		{
			return false;
		}
		
		PhysTriangle[] triangles = phys.getTriangles();
		boolean collided;

		for (PhysTriangle triangle : triangles)
		{
			collided = testTriangle(pos, dir, triangle, mat, intersectPoint);

			if (collided)
			{
				return true;
			}
		}

		return false;
	}

	private static Vector4f vertexHelper = new Vector4f();
	
	private static Vector3f vertex0 = new Vector3f();
	private static Vector3f vertex1 = new Vector3f();
	private static Vector3f vertex2 = new Vector3f();

	private static Vector3f edge1 = new Vector3f();
	private static Vector3f edge2 = new Vector3f();

	private static Vector3f h = new Vector3f();
	private static Vector3f s = new Vector3f();
	private static Vector3f q = new Vector3f();

	private static boolean testTriangle(Vector3f pos, Vector3f dir, PhysTriangle triangle, Matrix4f mat, Vector3f intersectPoint)
	{
		triangle.getVertex1(0, vertex0);
		triangle.getVertex1(1, vertex1);
		triangle.getVertex1(2, vertex2);

		convert(vertex0, mat);
		convert(vertex1, mat);
		convert(vertex2, mat);
		
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

	private static void convert(Vector3f vertex, Matrix4f mat)
	{
		vertexHelper.x = vertex.x;
		vertexHelper.y = vertex.y;
		vertexHelper.z = vertex.z;
		vertexHelper.w = 1;
		
		Matrix4f.transform(mat, vertexHelper, vertexHelper);
		
		vertex.x = vertexHelper.x;
		vertex.y = vertexHelper.y;
		vertex.z = vertexHelper.z;
	}
}
