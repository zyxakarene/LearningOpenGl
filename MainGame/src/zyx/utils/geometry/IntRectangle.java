package zyx.utils.geometry;

public class IntRectangle
{

	public int x, y;
	public int width, height;

	public IntRectangle()
	{
	}
	
	public IntRectangle(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void copyFrom(IntRectangle source)
	{
		this.x = source.x;
		this.y = source.y;
		this.width = source.width;
		this.height = source.height;
	}
}
