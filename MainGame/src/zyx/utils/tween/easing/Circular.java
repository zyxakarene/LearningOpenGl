package zyx.utils.tween.easing;

import zyx.utils.FloatMath;

public class Circular
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Circular()
	{
		in = this::in;
		out = this::out;
		inOut = this::inOut;
	}

	public float in(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		t = t / d;
		return -c * (FloatMath.sqrt(1 - t * t) - 1) + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		t = t / (d - 1);
		return c * FloatMath.sqrt(1 - t * t) + b;
	}

	public float inOut(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		t = t / (d / 2);
		if (t < 1)
		{
			return -c / 2 * (FloatMath.sqrt(1 - t * t) - 1) + b;
		}
		
		t -= 2;
		return c / 2 * (FloatMath.sqrt(1 - t * t) + 1) + b;
	}
}
