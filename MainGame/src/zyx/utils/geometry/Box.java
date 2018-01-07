package zyx.utils.geometry;

public class Box
{

	public float minX, maxX;
	public float minY, maxY;
	public float minZ, maxZ;
	
	public float width;
	public float debth;
	public float height;

	public Box(float width, float debth, float height)
	{
		this.width = width;
		this.debth = debth;
		this.height = height;
	}
}
