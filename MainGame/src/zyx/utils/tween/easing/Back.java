package zyx.utils.tween.easing;

public class Back
{

	public final IEase in;
	public final IEase out;
	public final IEase inOut;

	Back()
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

		float s = 1.70158f;

		t = t / d;
		return c * t * t * ((s + 1) * t - s) + b;
	}

	public float out(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;

		float s = 1.70158f;

		t = t / d - 1;
		return c * (t * t * ((s + 1) * t + s) + 1) + b;
	}

	public float inOut(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;

		float s = 1.70158f * 1.525f;
		t = t / (duration / 2);
		if (t < 1)
		{
			return c / 2 * (t * t * ((s + 1) * t - s)) + b;
		}
		
		return c / 2 * ((t -= 2) * t * ((s + 1) * t + s) + 2) + b;
	}

}
