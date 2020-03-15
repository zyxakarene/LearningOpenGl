package zyx.utils.tween.easing;

public class Linear
{
	public final IEase in;
	
	Linear()
	{
		in = this::in;
	}
	
	public float in(long startTime, long currentTime, float startValue, float endValue, int duration)
	{
		float t = currentTime - startTime;
		float b = startValue;
		float c = endValue - startValue;
		float d = duration;
		
		return c * t / d + b;
	}
}
