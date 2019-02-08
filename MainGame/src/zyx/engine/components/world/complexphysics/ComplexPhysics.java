package zyx.engine.components.world.complexphysics;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.worldpicker.calculating.PhysPicker;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;
import zyx.utils.interfaces.IUpdateable;

public final class ComplexPhysics implements IUpdateable
{
	private static final Vector3f HELPER_FROM = new Vector3f();
	private static final Vector3f HELPER_DIR_XY = new Vector3f();
	private static final Vector3f HELPER_DIR_Z = new Vector3f();
	private static final Vector3f HELPER_POINT = new Vector3f();
	
	private ArrayList<IPhysbox> meshes;
	private ArrayList<EntityCollider> colliders;

	private PhysPicker picker;
	
	public ComplexPhysics()
	{
		colliders = new ArrayList<>();
		meshes = new ArrayList<>();
		picker = new PhysPicker();
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
		boolean hasXY = false;
		boolean hasZ = false;
		int i;
		int j;

		for (i = 0; i < physLen; i++)
		{
			collider = colliders.get(i);
			collider.velocity.z -= 0.1f;
			
			collider.getPosition(HELPER_FROM);
			Print.out("Checking from", HELPER_FROM);
			
			HELPER_DIR_Z.set(collider.velocity);
			HELPER_DIR_Z.x = 0;
			HELPER_DIR_Z.y = 0;
			
			HELPER_DIR_XY.set(collider.velocity);
			HELPER_DIR_XY.z = 0;
			
			hasXY = HELPER_DIR_XY.x != 0 || HELPER_DIR_XY.y != 0;
			hasZ = HELPER_DIR_Z.z != 0;
			
			picker.maxDistance = collider.velocity.length() * elapsedTime;
			picker.maxDistance = picker.maxDistance * picker.maxDistance;
			
			if (hasXY)
			{
				HELPER_DIR_XY.normalise();
				Print.out(picker.maxDistance);
			}
			
			if (hasZ)
			{
				HELPER_DIR_Z.normalise();
			}
			
			
			for (j = 0; j < meshLen; j++)
			{
				collided = false;
				mesh = meshes.get(j);
				
				if (hasXY)
				{
					collided = picker.collided(HELPER_FROM, HELPER_DIR_XY, mesh, HELPER_POINT);
				}
				
				if (collided)
				{
					picker.collided(HELPER_FROM, HELPER_DIR_XY, mesh, HELPER_POINT);
					
					Print.out("Collided XY at", HELPER_POINT);
//					DebugPoint.addToScene(HELPER_POINT, 10000);
					collider.setPosition(HELPER_POINT);
					collider.velocity.x = 0;
					collider.velocity.y = 0;
				}
				
				if (hasZ)
				{
					collided = picker.collided(HELPER_FROM, HELPER_DIR_Z, mesh, HELPER_POINT);
				}
				
				if (collided)
				{
					Print.out("Collided Z at", HELPER_POINT);
//					DebugPoint.addToScene(HELPER_POINT, 10000);
					collider.setPosition(HELPER_POINT);
					collider.velocity.z = 0;
				}
			}
			
			if (!collided)
			{
				HELPER_POINT.x = HELPER_FROM.x + (collider.velocity.x * elapsedTime);
				HELPER_POINT.y = HELPER_FROM.y + (collider.velocity.y * elapsedTime);
				HELPER_POINT.z = HELPER_FROM.z + (collider.velocity.z * elapsedTime);
				collider.setPosition(HELPER_POINT);
			}
		}
	}
}
