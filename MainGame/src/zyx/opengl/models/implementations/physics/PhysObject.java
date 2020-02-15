package zyx.opengl.models.implementations.physics;

import zyx.utils.interfaces.IDisposeable;

public class PhysObject implements IDisposeable
{
	private PhysTriangle[] triangles;
	private short boneId;
	
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

	@Override
	public void dispose()
	{
		triangles = null;
	}
	
}
