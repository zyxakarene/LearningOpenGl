package zyx.utils.tween.easing;

public class Cubic
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Cubic()
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
		return c * t * t * t + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		t = t / d - 1;
		return c * (t * t * t + 1) + b;
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
			return c / 2 * t * t * t + b;
		}
		
		t -= 2;
		return c / 2 * (t * t * t + 2) + b;
	}
}
