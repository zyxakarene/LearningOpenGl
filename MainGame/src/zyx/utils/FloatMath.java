package zyx.utils;

import org.lwjgl.util.vector.Vector3f;

public class FloatMath
{

	public static final float PI = (float) Math.PI;

	public static final float RAD_TO_DEG = 180f / PI;
	public static final float DEG_TO_RAD = PI / 180f;

	public static float pow(float a, float b)
	{
		return (float) Math.pow(a, b);
	}

	public static float clamp(float val, float minimum, float maximum)
	{
		return max(minimum, min(maximum, val));
	}

	public static float max(float a, float b)
	{
		return a > b ? a : b;
	}

	public static float min(float a, float b)
	{
		return a < b ? a : b;
	}

	public static int floor(float value)
	{
		return (int) value;
	}

	public static int ceil(float value)
	{
		return (int) value + 1;
	}

	public static float abs(float value)
	{
		return Math.abs(value);
	}

	public static float toRadians(float degrees)
	{
		return degrees / 180.0f * PI;
	}

	public static float toDegrees(float radians)
	{
		return radians * 180.0f / PI;
	}

	public static float sin(float a)
	{
		return (float) Math.sin(a);
	}

	public static float asin(float a)
	{
		return (float) Math.asin(a);
	}

	public static float cos(float a)
	{
		return (float) Math.cos(a);
	}

	public static float acos(float a)
	{
		return (float) Math.acos(a);
	}

	public static float tan(float a)
	{
		return (float) Math.tan(a);
	}

	public static float atan(float a)
	{
		return (float) Math.atan(a);
	}

	public static float atan2(float y, float x)
	{
		return (float) Math.atan2(y, x);
	}

	public static float sqrt(float value)
	{
		return (float) Math.sqrt(value);
	}

	public static float random()
	{
		return (float) Math.random();
	}

	public static float square(float value)
	{
		return value * value;
	}

	public static float distance(Vector3f a, Vector3f b)
	{
		return distance(a, b, false);
	}

	public static float distance(Vector3f a, Vector3f b, boolean square)
	{
		return distance(a.x, a.y, a.z, b.x, b.y, b.z, square);
	}

	public static float distance(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		return distance(x1, y1, z1, x2, y2, z2, false);
	}

	public static float distance(float x1, float y1, float z1, float x2, float y2, float z2, boolean square)
	{
		float distance = ((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)) + ((z1 - z2) * (z1 - z2));

		return square ? sqrt(distance) : distance;
	}
}
