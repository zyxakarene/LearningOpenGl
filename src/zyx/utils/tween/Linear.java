package zyx.utils.tween;

public class Linear
{
	public static float lerp(float from, float to, float fraction)
	{
		return from + fraction * (to - from);
	}
}
