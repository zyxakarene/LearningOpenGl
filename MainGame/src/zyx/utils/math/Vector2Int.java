package zyx.utils.math;

public class Vector2Int
{

	public int x;
	public int y;

	public Vector2Int()
	{
	}

	public Vector2Int(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return String.format("Vector2Int{%s, %s}", x, y);
	}
}
