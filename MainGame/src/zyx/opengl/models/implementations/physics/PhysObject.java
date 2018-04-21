package zyx.opengl.models.implementations.physics;

public class PhysObject
{
	private final PhysTriangle[] triangles;
	private final short boneId;
	
	private int addedTriangles;

	public PhysObject(int triangleCount, short boneId)
	{
		this.triangles = new PhysTriangle[triangleCount];
		this.boneId = boneId;
		
		addedTriangles = 0;
	}

	void addTriangle(PhysTriangle triangle)
	{
		triangles[addedTriangles] = triangle;
		addedTriangles++;
	}
	
	public PhysTriangle[] getTriangles()
	{
		return triangles;
	}

	public short getBoneId()
	{
		return boneId;
	}
	
}
