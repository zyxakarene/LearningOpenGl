package zyx.engine.utils.worldpicker.calculating;

import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IPhysbox;

public class PhysPicker extends AbstractPicker
{

	private final float EPSILON = 0.0000001f;

	private final Matrix4f INVERT_TRANSPOSE = new Matrix4f();

	private final Vector3f EDGE_1 = new Vector3f();
	private final Vector3f EDGE_2 = new Vector3f();

	private final Vector3f H = new Vector3f();
	private final Vector3f S = new Vector3f();
	private final Vector3f Q = new Vector3f();

	private final LinkedList<Vector3f> positions = new LinkedList<>();

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

		Matrix4f.load(mat, INVERT_TRANSPOSE);
		Matrix4f.invert(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
		Matrix4f.transpose(INVERT_TRANSPOSE, INVERT_TRANSPOSE);

		positions.clear();
		
		for (PhysTriangle triangle : triangles)
		{
			collided = testTriangle(pos, dir, triangle, mat, intersectPoint);

			if (collided)
			{
				positions.add(new Vector3f(intersectPoint));
			}
		}

		if (positions.isEmpty())
		{
			return false;
		}
		else if (positions.size() == 1)
		{
			return true;
		}
		else
		{
			float closestDistance = Float.MAX_VALUE;
			float currentDistance;
			Vector3f closestPos = new Vector3f(intersectPoint);
			Vector3f currentPos;
			
			while (positions.isEmpty() == false)
			{				
				currentPos = positions.remove();
				currentDistance = FloatMath.distance(currentPos, pos);
				if (currentDistance < closestDistance)
				{
					closestDistance = currentDistance;
					closestPos = currentPos;
				}
			}
			
			intersectPoint.set(closestPos);
			return true;
		}
	}

	private boolean testTriangle(Vector3f pos, Vector3f dir, PhysTriangle triangle, Matrix4f mat, Vector3f intersectPoint)
	{
		NORMAL.set(triangle.normal);

		transformVertex(NORMAL, INVERT_TRANSPOSE);
		
		float diff = Vector3f.dot(NORMAL, dir);
		if (diff > 0)
		{
			//Backfacing triangle
			return false;
		}
		VERTEX_1.set(triangle.v1);
		VERTEX_2.set(triangle.v2);
		VERTEX_3.set(triangle.v3);
		transformVertex(VERTEX_1, mat);
		transformVertex(VERTEX_2, mat);
		transformVertex(VERTEX_3, mat);
		
		Vector3f.sub(VERTEX_2, VERTEX_1, EDGE_1);
		Vector3f.sub(VERTEX_3, VERTEX_1, EDGE_2);

		Vector3f.cross(dir, EDGE_2, H);
		float a = Vector3f.dot(EDGE_1, H);

		if (a > -EPSILON && a < EPSILON)
		{
			return false;
		}

		float f = 1 / a;

		Vector3f.sub(pos, VERTEX_1, S);
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
