package zyx.utils.geometry;

public class Box
{

	public float minX, maxX;
	public float minY, maxY;
	public float minZ, maxZ;

	public Box(float minX, float maxX, float minY, float maxY, float minZ, float maxZ)
	{
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}
}
