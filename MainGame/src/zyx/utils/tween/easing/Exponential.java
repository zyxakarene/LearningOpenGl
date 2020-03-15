package zyx.utils.tween.easing;

import zyx.utils.FloatMath;

public class Exponential
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Exponential()
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

		if (t == 0)
		{
			return b;
		}
		else
		{
			return c * FloatMath.pow(2, 10 * (t / d - 1)) + b;
		}
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		if (t == d)
		{
			return b + c;
		}
		else
		{
			return c * (-FloatMath.pow(2, -10 * t / d) + 1) + b;
		}
	}

	public float inOut(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		if (t == 0)
		{
			return b;
		}
		else if (t == d)
		{
			return b + c;
		}
		else if ((t /= d / 2) < 1)
		{
			return c / 2 * FloatMath.pow(2, 10 * (t - 1)) + b;
		}
		else
		{
			t--;
			return c / 2 * (-FloatMath.pow(2, -10 * t) + 2) + b;
		}
	}
}
