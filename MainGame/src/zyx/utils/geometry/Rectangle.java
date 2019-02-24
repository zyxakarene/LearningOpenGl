package zyx.utils.geometry;

public class Rectangle
{

	public float x, y;
	public float width, height;

	public Rectangle()
	{
	}
	
	public Rectangle(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void copyFrom(Rectangle source)
	{
		this.x = source.x;
		this.y = source.y;
		this.width = source.width;
		this.height = source.height;
	}
}
