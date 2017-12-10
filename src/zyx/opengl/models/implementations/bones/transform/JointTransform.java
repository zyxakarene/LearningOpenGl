package zyx.opengl.models.implementations.bones.transform;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;

public class JointTransform implements IDisposeable
{

	private Quaternion rotation;
	private Vector3f position;

	public JointTransform(float x, float y, float z, float rotX, float rotY, float rotZ, float rotW)
	{
		rotation = SharedPools.QUARERNION_POOL.getInstance();
		position = SharedPools.VECTOR_POOL.getInstance();
		
		position.set(x, y, z);
		rotation.set(rotX, rotY, rotZ, rotW);
	}

	public void getPosition(Vector3f out)
	{
		out.x = position.x;
		out.y = position.y;
		out.z = position.z;
	}
	
	public void getRotation(Quaternion out)
	{
		out.x = rotation.x;
		out.y = rotation.y;
		out.z = rotation.z;
		out.w = rotation.w;
	}

	@Override
	public void dispose()
	{
		SharedPools.QUARERNION_POOL.releaseInstance(rotation);
		SharedPools.VECTOR_POOL.releaseInstance(position);
		
		rotation = null;
		position = null;
	}
}
