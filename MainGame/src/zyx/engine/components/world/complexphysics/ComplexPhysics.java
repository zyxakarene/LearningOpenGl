package zyx.engine.components.world.complexphysics;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.ColliderInfo;
import zyx.engine.utils.worldpicker.calculating.PhysPicker;
import zyx.utils.FloatMath;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;
import zyx.utils.interfaces.IUpdateable;

public final class ComplexPhysics implements IUpdateable
{

	private static final Vector3f HELPER_FROM = new Vector3f();
	private static final Vector3f HELPER_DIR = new Vector3f();
	private static final Vector3f HELPER_POINT = new Vector3f();

	private ColliderInfo out;

	private ArrayList<IPhysbox> meshes;
	private ArrayList<EntityCollider> colliders;

	private PhysPicker picker;

	public ComplexPhysics()
	{
		colliders = new ArrayList<>();
		meshes = new ArrayList<>();
		picker = new PhysPicker();
		out = new ColliderInfo();
	}

	public void addCollider(EntityCollider collider)
	{
		if (colliders.contains(collider) == false)
		{
			colliders.add(collider);
		}
	}

	public void removeCollider(EntityCollider collider)
	{
		colliders.remove(collider);
	}

	public void addMesh(IPhysbox mesh)
	{
		if (meshes.contains(mesh) == false)
		{
			meshes.add(mesh);
		}
	}

	public void removeMesh(IPhysbox mesh)
	{
		meshes.remove(mesh);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (elapsedTime >= 200)
		{
			Print.out("High elapsedTime, setting to 16. Was", elapsedTime);
			elapsedTime = 16;
		}

		int physLen = colliders.size();
		int meshLen = meshes.size();

		IPhysbox mesh;
		EntityCollider collider;
		boolean collided = false;
		int i;
		int j;

		for (i = 0; i < physLen; i++)
		{
			collider = colliders.get(i);
			collider.velocity.z -= 0.05f;

			collider.getPosition(HELPER_FROM);
			HELPER_FROM.x += (collider.lastHitNormal.x * 0.1f);
			HELPER_FROM.y += (collider.lastHitNormal.y * 0.1f);
			HELPER_FROM.z += (collider.lastHitNormal.z * 0.1f);

			HELPER_DIR.set(collider.velocity);
			HELPER_DIR.normalise();

			Vector3f originalVelocity = new Vector3f(collider.velocity);
			originalVelocity.scale(-1);

			picker.maxDistance = collider.velocity.length() * elapsedTime;
			picker.maxDistance = picker.maxDistance * picker.maxDistance;
			Print.out("Dist:", picker.maxDistance);
			for (j = 0; j < meshLen && !collided; j++)
			{
				mesh = meshes.get(j);

				picker.collided(HELPER_FROM, HELPER_DIR, mesh, out);

				if (out.hasCollision)
				{
					collided = true;
					Print.out("Collided at", out.intersectPoint, "Angle:", out.triangleAngle);
					DebugPoint point = DebugPoint.addToScene(out.intersectPoint, 500);
					point.setScale(0.02f, 0.02f, 0.02f);

					collider.velocity.x = 0;
					collider.velocity.y = 0;
					collider.velocity.z = 0;

					float rads = FloatMath.acos(out.triangleAngle);
					float degrees = FloatMath.toDegrees(rads);
					Print.out("cosTheta:", out.triangleAngle, "-", degrees, "angles");
					if (out.triangleAngle < 0.707 && out.triangleAngle > -0.707)
					{
						ColliderInfo testOut = new ColliderInfo();
						picker.collided(HELPER_FROM, HELPER_DIR, mesh, testOut);
						
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
						Vector3f slideDir = new Vector3f(out.triangle.normal);
						slideDir.normalise();

						Vector3f supposedFrom = new Vector3f(HELPER_FROM);
						supposedFrom.x += HELPER_DIR.x * picker.maxDistance;
						supposedFrom.y += HELPER_DIR.y * picker.maxDistance;
						supposedFrom.z += HELPER_DIR.z * picker.maxDistance;

						picker.collidedSingular(supposedFrom, slideDir, out.triangle, out);

						if (out.hasCollision)
						{
							out.intersectPoint.x += slideDir.x * 0.01f;
							out.intersectPoint.y += slideDir.y * 0.01f;
							//out.intersectPoint.z += 0.2f;
							HELPER_FROM.x = out.intersectPoint.x;
							HELPER_FROM.y = out.intersectPoint.y;
							HELPER_FROM.z = out.intersectPoint.z;

							point = DebugPoint.addToScene(out.intersectPoint, 500);
							point.setScale(0.01f, 0.01f, 0.01f);
							point.setRotation(45f, 45f, 45f);
							collider.setPosition(out.intersectPoint);
						}
					}
					else if (FloatMath.isAlmost(out.triangleAngle, 1, 0.0001f))
					{
						Print.out("ON FLAT");
						HELPER_FROM.x = out.intersectPoint.x;
						HELPER_FROM.y = out.intersectPoint.y;
						HELPER_FROM.z = out.intersectPoint.z;
					}
					else
					{
						Vector3f start = new Vector3f(HELPER_FROM);
						Vector3f to = new Vector3f(Vector3f.add(start, originalVelocity, null));

						float xyDistance = FloatMath.distance(start.x, start.y, 0, to.x, to.y, 0);

						float z = xyDistance * FloatMath.tan(degrees) * -1;
						
						Print.out("Moving Z:", z);
						if (z < 0)
						{
							Print.out("But it was canceled!");
							z = 0;
						}
						
						HELPER_FROM.x = out.intersectPoint.x;
						HELPER_FROM.y = out.intersectPoint.y;
						HELPER_FROM.z = out.intersectPoint.z + z;
					}
				}
			}

			if (!collided)
			{
				HELPER_FROM.x = HELPER_FROM.x + (collider.velocity.x * elapsedTime) - (collider.lastHitNormal.x * 0.1f);
				HELPER_FROM.y = HELPER_FROM.y + (collider.velocity.y * elapsedTime) - (collider.lastHitNormal.y * 0.1f);
				HELPER_FROM.z = HELPER_FROM.z + (collider.velocity.z * elapsedTime) - (collider.lastHitNormal.z * 0.1f);
			}

			collider.setPosition(HELPER_FROM);
		}
	}
}
