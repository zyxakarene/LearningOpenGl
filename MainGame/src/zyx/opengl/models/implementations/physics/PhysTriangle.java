package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;

public class PhysTriangle
{
	public Vector3f v1;
	public Vector3f v2;
	public Vector3f v3;
	public Vector3f normal;

	public PhysTriangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f normal)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.normal = normal;
	}

	void dispose()
	{
		v1 = null;
		v2 = null;
		v3 = null;
		
		normal = null;
	}
}
