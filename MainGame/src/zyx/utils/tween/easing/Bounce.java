package zyx.utils.tween.easing;

public class Bounce
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Bounce()
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

		return bounceIn(t, b, c, d);
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		return bounceOut(t, b, c, d);
	}

	public float inOut(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		if (t < d / 2)
		{
			return bounceIn(t * 2, 0, c, d) * 0.5f + b;
		}
		else
		{
			return bounceOut(t * 2 - d, 0, c, d) * 0.5f + c * 0.5f + b;
		}
	}

	private float bounceIn(float t, float b, float c, float d)
	{
		return c - bounceOut(d - t, 0, c, d) + b;
	}

	private float bounceOut(float t, float b, float c, float d)
	{
		t = t / d;
		if (t < (1 / 2.75))
		{
			return c * (7.5625f * t * t) + b;
		}
		else if (t < (2 / 2.75))
		{
			t -= (1.5f / 2.75f);
			return c * (7.5625f * t * t + 0.75f) + b;
		}
		else if (t < (2.5 / 2.75))
		{
			t -= (2.25f / 2.75f);
			return c * (7.5625f * t * t + 0.9375f) + b;
		}
		else
		{
			t -= (2.625f / 2.75f);
			return c * (7.5625f * t * t + 0.984375f) + b;
		}
	}
}
