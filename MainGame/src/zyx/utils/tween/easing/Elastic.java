package zyx.utils.tween.easing;

import zyx.utils.FloatMath;

public class Elastic
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Elastic()
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

		t = t / d;
		if (t == 1)
		{
			return b + c;
		}

		float p = d * 0.3f;
		float s = p / 4;
		t--;

		return -(c * FloatMath.pow(2, 10 * t) * FloatMath.sin((t * d - s) * (2 * FloatMath.PI) / p)) + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		if (t == 0)
		{
			return b;
		}

		t = t / d;
		if (t == 1)
		{
			return b + c;
		}

		float p = d * 0.3f;
		float s = p / 4;

		return c * FloatMath.pow(2, -10 * t) * FloatMath.sin((t * d - s) * (2 * FloatMath.PI) / p) + c + b;
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

		t = t / (d / 2);
		if (t == 2)
		{
			return b + c;
		}

		float p = d * (0.3f * 1.5f);
		float a = c;
		float s = p / 4;
		if (t < 1)
		{
			t--;
			return -0.5f * (a * FloatMath.pow(2, 10 * t) * FloatMath.sin((t * d - s) * (2 * FloatMath.PI) / p)) + b;
		}
		
		t--;
		return a * FloatMath.pow(2, -10 * t) * FloatMath.sin((t * d - s) * (2 * FloatMath.PI) / p) * .5f + c + b;
	}
}
