package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;

public class PhysTriangle
{
	public final Vector3f v1;
	public final Vector3f v2;
	public final Vector3f v3;
	public final Vector3f normal;

	public PhysTriangle()
	{
		v1 = new Vector3f();
		v2 = new Vector3f();
		v3 = new Vector3f();
		normal = new Vector3f();
	}
	
	public PhysTriangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f normal)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.normal = normal;
	}
}
