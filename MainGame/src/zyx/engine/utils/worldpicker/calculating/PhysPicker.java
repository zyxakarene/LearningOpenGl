package zyx.engine.utils.worldpicker.calculating;

import java.util.LinkedList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.ColliderInfo;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysObject;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.FloatMath;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class PhysPicker extends AbstractPicker
{

	private final float EPSILON = 0.0000001f;

	private final Matrix4f INVERT_TRANSPOSE = new Matrix4f();
	private final Matrix4f BONE_TRANSFORM = new Matrix4f();

	private final Vector3f EDGE_1 = new Vector3f();
	private final Vector3f EDGE_2 = new Vector3f();

	private final Vector3f H = new Vector3f();
	private final Vector3f S = new Vector3f();
	private final Vector3f Q = new Vector3f();

	private final LinkedList<Vector3f> positions = new LinkedList<>();

	public float maxDistance = Float.MAX_VALUE;

	@Override
	public void collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, ColliderInfo out)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		out.hasCollision = false;

		if (phys == null)
		{
			return;
		}

		boolean hitOBB = RayOBB.hit(phys.getBoundingBox(), mat, pos, dir);
		if (!hitOBB)
		{
			return;
		}

		Vector3f posCopy = new Vector3f(pos);
		posCopy.x -= dir.x * 0.01f;
		posCopy.y -= dir.y * 0.01f;
		posCopy.z -= dir.z * 0.01f;
		
		positions.clear();

		PhysObject[] objects = phys.getObjects();

		for (PhysObject object : objects)
		{
			short boneId = object.getBoneId();
			PhysTriangle[] triangles = object.getTriangles();
			Matrix4f boneMatrix = physContainer.getBoneMatrix(boneId);

			Matrix4f.mul(mat, boneMatrix, BONE_TRANSFORM);

			Matrix4f.load(BONE_TRANSFORM, INVERT_TRANSPOSE);
			Matrix4f.invert(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			Matrix4f.transpose(INVERT_TRANSPOSE, INVERT_TRANSPOSE);

			boolean collided;
			for (PhysTriangle triangle : triangles)
			{
				collided = testTriangle(posCopy, dir, triangle, BONE_TRANSFORM, out);

				if (collided)
				{
					positions.add(new Vector3f(out.intersectPoint));
				}
			}

		}

		if (positions.isEmpty())
		{
			return;
		}
		else if (positions.size() == 1)
		{
			Vector3f collidePoint = positions.get(0);
			float distance = FloatMath.distance(collidePoint, posCopy);

			if (distance <= maxDistance)
			{
//				collidePoint.z += 1f;
				out.intersectPoint.set(collidePoint);
				out.hasCollision = true;
			}
		}
		else
		{
			boolean hitWithinDistance = false;
			float closestDistance = maxDistance;
			float currentDistance;
			Vector3f closestPos = new Vector3f(out.intersectPoint);
			Vector3f currentPos;

			while (positions.isEmpty() == false)
			{
				currentPos = positions.remove();
				currentDistance = FloatMath.distance(currentPos, posCopy);
				if (currentDistance < closestDistance)
				{
					hitWithinDistance = true;
					closestDistance = currentDistance;
					closestPos = currentPos;
				}
			}

			out.intersectPoint.set(closestPos);
			out.hasCollision = hitWithinDistance;
		}
	}

	private boolean testTriangle(Vector3f pos, Vector3f dir, PhysTriangle triangle, Matrix4f mat, ColliderInfo out)
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

		if (VERTEX_1.x == 25 && VERTEX_1.y == 25 && VERTEX_1.z == 0)
		{
			if (VERTEX_2.x == -25 && VERTEX_2.y == 25 && VERTEX_2.z == 0)
			{
				if (VERTEX_3.x == -25 && VERTEX_3.y == -25 && VERTEX_3.z == 0)
				{
					Print.out("Testing test Triangle!");
				}
			}
		}

		if (KeyboardData.data.wasPressed(Keyboard.KEY_P))
		{
			DebugPoint.addToScene(VERTEX_1, 1000);
			DebugPoint.addToScene(VERTEX_2, 1000);
			DebugPoint.addToScene(VERTEX_3, 1000);
		}

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
			out.triangle.normal.set(NORMAL);
			out.triangle.v1.set(VERTEX_1);
			out.triangle.v2.set(VERTEX_2);
			out.triangle.v3.set(VERTEX_3);

			out.intersectPoint.set(pos);
			out.intersectPoint.x += dir.x * t;
			out.intersectPoint.y += dir.y * t;
			out.intersectPoint.z += dir.z * t;

			out.triangleAngle = Vector3f.dot(NORMAL, new Vector3f(0, 0, 1));
			return true;
		}
		else // This means that there is a line intersection but not a ray intersection.
		{
			return false;
		}
	}

	private boolean testTriangleSimple(Vector3f pos, Vector3f dir, PhysTriangle triangle, ColliderInfo out)
	{
		NORMAL.set(triangle.normal);

		VERTEX_1.set(triangle.v1);
		VERTEX_2.set(triangle.v2);
		VERTEX_3.set(triangle.v3);

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
			out.triangle.normal.set(NORMAL);
			out.triangle.v1.set(VERTEX_1);
			out.triangle.v2.set(VERTEX_2);
			out.triangle.v3.set(VERTEX_3);

			out.intersectPoint.set(pos);
			out.intersectPoint.x += dir.x * t;
			out.intersectPoint.y += dir.y * t;
			out.intersectPoint.z += dir.z * t;

			out.triangleAngle = Vector3f.dot(NORMAL, dir);
			return true;
		}
		else // This means that there is a line intersection but not a ray intersection.
		{
			return false;
		}
	}

	public void collidedSingular(Vector3f pos, Vector3f dir, PhysTriangle triangle, ColliderInfo out)
	{
		testTriangleSimple(pos, dir, triangle, out);
	}
}
