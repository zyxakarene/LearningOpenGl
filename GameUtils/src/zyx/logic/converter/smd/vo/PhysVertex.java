package zyx.logic.converter.smd.vo;

public class PhysVertex
{
	public final float x, y, z;
	public final float normX, normY, normZ;

	public PhysVertex(float x, float y, float z, float normX, float normY, float normZ)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.normX = normX;
		this.normY = normY;
		this.normZ = normZ;
	}

	@Override
	public String toString()
	{
		return "{" + x +"," + y + "," + z + "}";
	}
}
