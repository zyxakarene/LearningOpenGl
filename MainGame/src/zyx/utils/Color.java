package zyx.utils;

import org.lwjgl.util.vector.Vector4f;

public class Color
{

	public static float getRed(int color)
	{
		return (color >> 16) & 0xff;
	}

	public static float getGreen(int color)
	{
		return (color >> 8) & 0xff;
	}

	public static float getBlue(int color)
	{
		return color & 0xff;
	}

	public static Vector4f toVector(int color)
	{
		return toVector(color, null);
	}

	public static Vector4f toVector(int color, Vector4f out)
	{
		if (out == null)
		{
			out = new Vector4f();
		}
		
		out.x = ((color >> 16) & 0xff) / 255f;
		out.y = ((color >> 8)  & 0xff) / 255f;
		out.z = ((color)  & 0xff) / 255f;
		
		return out;
	}
}
