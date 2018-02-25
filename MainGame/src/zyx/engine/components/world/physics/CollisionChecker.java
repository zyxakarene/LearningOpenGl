package zyx.engine.components.world.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.Collider;
import zyx.utils.FloatMath;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.Box;

public class CollisionChecker
{

	private static AbstractColliderMover MOVER_X = new BoxMoverX();
	private static AbstractColliderMover MOVER_Y = new BoxMoverY();
	private static AbstractColliderMover MOVER_Z = new BoxMoverZ();
	
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
	}

	public static void check(Collider staticCollider, Collider physCollider)
	{
		if (staticCollider instanceof BoxCollider && physCollider instanceof BoxCollider)
		{
			BoxCollider staticBox = (BoxCollider) staticCollider;
			BoxCollider physBox = (BoxCollider) physCollider;

			checkBoxBox(staticBox, physBox);

			if (COLLISION.hasCollision())
			{
				fixBoxCollisions(staticBox, physBox, 0);
			}
		}
	}

	private static void fixBoxCollisions(BoxCollider staticBox, BoxCollider physBox, int count)
	{
		if (count >= 4)
		{
			Print.out("Too many checks! PANIC!");
			return;
		}
		
		Box physBound = physBox.boundingBox;
		Box statBound = staticBox.boundingBox;

		float distIntoXGround = FloatMath.abs(statBound.maxX - physBound.minX);
		float distIntoXCeil = FloatMath.abs(physBound.maxX - statBound.minX);

		float distIntoYGround = FloatMath.abs(statBound.maxY - physBound.minY);
		float distIntoYCeil = FloatMath.abs(physBound.maxY - statBound.minY);
		
		float distIntoZGround = FloatMath.abs(statBound.maxZ - physBound.minZ);
		float distIntoZCeil = FloatMath.abs(physBound.maxZ - statBound.minZ);
		
		float leastX = distIntoXCeil < distIntoXGround ? distIntoXCeil : distIntoXGround;
		float leastY = distIntoYCeil < distIntoYGround ? distIntoYCeil : distIntoYGround;
		float leastZ = distIntoZCeil < distIntoZGround ? distIntoZCeil : distIntoZGround;
		
		if(leastZ <= 10 && distIntoZGround < distIntoZCeil)
		{
			leastZ = 0;
		}
		
		if (leastX < leastY && leastX < leastZ)
		{
			//X is least
			moveBox(statBound.maxX - physBound.minX, physBound.maxX - statBound.minX, statBound, physBox, MOVER_X);
		}
		else if (leastY < leastZ && leastY < leastX)
		{
			//Y is least
			moveBox(statBound.maxY - physBound.minY, physBound.maxY - statBound.minY, statBound, physBox, MOVER_Y);
		}
		else
		{
			//Z is least
			moveBox(statBound.maxZ - physBound.minZ, physBound.maxZ - statBound.minZ, statBound, physBox, MOVER_Z);
		}
		
		checkBoxBox(physBox, staticBox);
		if (COLLISION.hasCollision())
		{
			fixBoxCollisions(staticBox, physBox, count+1);
		}
	}
	
	private static void moveBox(float distIntoGround, float distIntoCeil, Box statBound, BoxCollider physCollider, AbstractColliderMover mover)
	{
		Box physBounds = physCollider.boundingBox;
		
		if (distIntoGround < distIntoCeil)
		{
			mover.onMoveTo(statBound, physCollider, true);
		}
		else
		{
			mover.onMoveTo(statBound, physCollider, false);
		}
	}
}
