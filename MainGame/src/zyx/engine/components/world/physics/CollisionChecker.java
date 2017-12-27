package zyx.engine.components.world.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.Collider;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.Box;

public class CollisionChecker
{

	private static final Collision COLLISION = new Collision();

	private static final Vector3f HELPER_A = new Vector3f();
	private static final Vector3f HELPER_B = new Vector3f();
	private static final Vector3f DIR = new Vector3f();

	private static void checkBoxBox(BoxCollider colliderA, BoxCollider colliderB)
	{
		Box a = colliderA.boundingBox;
		Box b = colliderB.boundingBox;

		COLLISION.x = a.minX < b.maxX && a.maxX > b.minX;
		COLLISION.y = a.minY < b.maxY && a.maxY > b.minY;
		COLLISION.z = a.minZ < b.maxZ && a.maxZ > b.minZ;

		if (COLLISION.hasCollision())
		{
			BoxCollider toMove = a.minZ < b.minZ ? colliderB : colliderA;
			toMove.velocity.z = 2;
		}
	}

	public static void check(Collider a, Collider b)
	{
		if (a == b)
		{
			return;
		}

		if (a instanceof BoxCollider && b instanceof BoxCollider)
		{
			BoxCollider boxA = (BoxCollider) a;
			BoxCollider boxB = (BoxCollider) b;

			checkBoxBox(boxA, boxB);

			if (COLLISION.hasCollision())
			{
				fixBoxCollisions(boxA, boxB);
			}
		}
	}

	private static void fixBoxCollisions(BoxCollider a, BoxCollider b)
	{
		Box boxA = a.boundingBox;
		Box boxB = b.boundingBox;

		float minZ_a = a.boundingBox.minZ;
		float maxZ_a = a.boundingBox.maxZ;
		float minZ_b = b.boundingBox.minZ;
		float maxZ_b = b.boundingBox.maxZ;

		a.velocity.x *= 0.5;
		a.velocity.y *= 0.5;
		b.velocity.x *= 0.5;
		b.velocity.y *= 0.5;

		a.getPosition(HELPER_A);
		b.getPosition(HELPER_B);

		DIR.x = HELPER_A.x - HELPER_B.x;
		DIR.y = HELPER_A.y - HELPER_B.y;
		DIR.z = 0;
		if (DIR.length() != 0)
		{
			DIR.normalise();

			if (a.isStatic == false)
			{
				a.velocity.set(DIR);
			}
			DIR.negate();
			if (b.isStatic == false)
			{
				b.velocity.set(DIR);
			}
		}

		BoxCollider toMoveUp = boxA.minZ < boxB.minZ ? b : a;
		toMoveUp.velocity.z = 2;

	}
}
