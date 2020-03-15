package zyx.utils.tween.easing;

public interface IEase
{

	public float ease(long startTime, long currentTime, float startValue, float endValue, int duration);

}
