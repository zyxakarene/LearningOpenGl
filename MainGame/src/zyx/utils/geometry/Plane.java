package zyx.utils.geometry;

import zyx.utils.FloatMath;

public class Plane
{
	
	public float a;
	public float b;
	public float c;
	public float d;

	public Plane()
	{
		this(0, 0, 0, 0);
	}
	
	public Plane(float a, float b, float c, float d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public void normalize()
	{
		float lengthSquared = a * a + b * b + c * c;
		float length = FloatMath.square(lengthSquared);
		
		float scale = 1 / length;
		a = a * scale;
		b = b * scale;
		c = c * scale;
		d = d * scale;
	}

	@Override
	public String toString()
	{
		return String.format("Plane{a:%s, b:%s, c:%s, d:%s}", a, b, c, d);
	}
	
	
}
