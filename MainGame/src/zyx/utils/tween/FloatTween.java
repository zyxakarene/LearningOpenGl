package zyx.utils.tween;

public abstract class FloatTween<T> extends BaseTween<T, Float>
{
	private float startValue;
	private float endValue;

	@Override
	Float getTweenValue(long timestamp)
	{
		return function.ease(startTime, timestamp, startValue, endValue, duration);
	}

	@Override
	void setValues(Float startValue, Float endValue)
	{
		this.startValue = startValue;
		this.endValue = endValue;
	}
}
