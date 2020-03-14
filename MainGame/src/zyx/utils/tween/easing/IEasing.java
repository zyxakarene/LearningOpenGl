package zyx.utils.tween.easing;

public interface IEasing
{
	 float ease(long startTime, long currentTime, float startValue, float endValue, int duration);
}
