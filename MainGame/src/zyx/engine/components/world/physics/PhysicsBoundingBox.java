package zyx.engine.components.world.physics;

import zyx.utils.geometry.Box;

class PhysicsBoundingBox extends Box
{
	public float width;
	public float debth;
	public float height;

	public PhysicsBoundingBox(float width, float debth, float height)
	{
		super();
		
		this.width = width;
		this.debth = debth;
		this.height = height;
	}
	
	public PhysicsBoundingBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ)
	{
		super(minX, maxX, minY, maxY, minZ, maxZ);
	}

}
