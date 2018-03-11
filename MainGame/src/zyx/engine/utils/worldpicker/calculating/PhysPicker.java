package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.interfaces.IPhysbox;

public class PhysPicker extends AbstractPicker
{

	private static final float EPSILON = 0.0000001f;

	private static final Vector3f VERTEX_0 = new Vector3f();
	private static final Vector3f VERTEX_1 = new Vector3f();
	private static final Vector3f VERTEX_2 = new Vector3f();

	private static final Vector3f EDGE_1 = new Vector3f();
	private static final Vector3f EDGE_2 = new Vector3f();

	private static final Vector3f H = new Vector3f();
	private static final Vector3f S = new Vector3f();
	private static final Vector3f Q = new Vector3f();

	@Override
	public boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
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

	private boolean testTriangle(Vector3f pos, Vector3f dir, PhysTriangle triangle, Matrix4f mat, Vector3f intersectPoint)
	{
		triangle.getVertex1(0, VERTEX_0);
		triangle.getVertex1(1, VERTEX_1);
		triangle.getVertex1(2, VERTEX_2);

		transformVertex(VERTEX_0, mat);
		transformVertex(VERTEX_1, mat);
		transformVertex(VERTEX_2, mat);

		Vector3f.sub(VERTEX_1, VERTEX_0, EDGE_1);
		Vector3f.sub(VERTEX_2, VERTEX_0, EDGE_2);

		Vector3f.cross(dir, EDGE_2, H);
		float a = Vector3f.dot(EDGE_1, H);

		if (a > -EPSILON && a < EPSILON)
		{
			return false;
		}

		float f = 1 / a;

		Vector3f.sub(pos, VERTEX_0, S);
		float u = f * Vector3f.dot(S, H);
		if (u < 0 || u > 1)
		{
			return false;
		}

		Vector3f.cross(S, EDGE_1, Q);
		float v = f * Vector3f.dot(dir, Q);
		if (v < 0 || u + v > 1)
		{
			return false;
		}

		// At this stage we can compute t to find out where the intersection point is on the line.
		float t = f * Vector3f.dot(EDGE_2, Q);
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
