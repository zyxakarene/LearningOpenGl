package zyx.utils.tween.easing;

import zyx.utils.FloatMath;

public class Sine
{

	private static final float HALF_PI = FloatMath.PI / 2f;

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Sine()
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

		return -c * FloatMath.cos(t / d * HALF_PI) + c + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		return c * FloatMath.sin(t / d * HALF_PI) + b;
	}

	public float inOut(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		return -c / 2 * (FloatMath.cos(FloatMath.PI * t / d) - 1) + b;
	}
}
