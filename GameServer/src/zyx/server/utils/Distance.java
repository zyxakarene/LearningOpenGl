package zyx.server.utils;

import zyx.server.world.entity.WorldEntity;

public class Distance
{
	public static float between(WorldEntity a, WorldEntity b)
	{
		return between(a.x, a.y, a.z, b.x, b.y, b.z);
	}
	
	public static float between(WorldEntity a, float x, float y, float z)
	{
		return between(a.x, a.y, a.z, x, y, z);
	}
	
	public static float between(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		float dX = x2 - x1;
		float dY = y2 - y1;
		float dZ = z2 - z1;
		
		return (dX * dX) + (dY * dY) + (dZ * dZ);
	}
}
