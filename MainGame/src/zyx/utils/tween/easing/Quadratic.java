package zyx.utils.tween.easing;

public class Quadratic
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Quadratic()
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
		return c * t * t + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		t = t / d;
		return -c * t * (t - 2) + b;
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
			return c / 2 * t * t + b;
		}

		t = t - 1;
		return -c / 2 * (t * (t - 2) - 1) + b;
	}
}
