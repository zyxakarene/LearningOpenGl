package zyx.utils;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Color
{

	private static final Vector3f HELPER_3 = new Vector3f();
	private static final Vector4f HELPER_4 = new Vector4f();
	
	public static float getAlpha(long color)
	{
		return (color >> 24) & 0xff; 
	}
	
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

	public static Vector3f toVector(int color)
	{
		return toVector(color, HELPER_3);
	}

	public static Vector4f toVector(long color)
	{
		return toVector(color, HELPER_4);
	}

	public static Vector3f toVector(int color, Vector3f out)
	{
		if (out == null)
		{
			out = HELPER_3;
		}
		
		out.x = ((color >> 16) & 0xff) / 255f;
		out.y = ((color >> 8)  & 0xff) / 255f;
		out.z = ((color)  & 0xff) / 255f;
		
		return out;
	}

	public static Vector4f toVector(long color, Vector4f out)
	{
		if (out == null)
		{
			out = HELPER_4;
		}
		
		out.w = ((color >> 24) & 0xff) / 255f;
		out.x = ((color >> 16) & 0xff) / 255f;
		out.y = ((color >> 8)  & 0xff) / 255f;
		out.z = ((color)  & 0xff) / 255f;
		
		return out;
	}
}
