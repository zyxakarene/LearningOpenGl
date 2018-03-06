package zyx.opengl.models.implementations.physics;

public class PhysTriangle
{
	final PhysVertex v1;
	final PhysVertex v2;
	final PhysVertex v3;

	public PhysTriangle(PhysVertex v1, PhysVertex v2, PhysVertex v3)
	{
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
}
