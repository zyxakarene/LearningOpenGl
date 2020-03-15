package zyx.utils.tween;

import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.tween.easing.EasingFunction;
import zyx.utils.tween.easing.IEase;

public abstract class BaseTween<T, V> implements IUpdateable, IDisposeable
{
	boolean completed;
	boolean started;

	long startTime;
	int duration;
	IEase function;
	
	private T target;
	private long endTime;
	
	BaseTween()
	{
		completed = false;
		started = false;
	}
	
	public BaseTween setTweenData(T target, V startValue, V endValue, int duration, EasingFunction easeType)
	{
		setValues(startValue, endValue);
		
		this.target = target;
		this.duration = duration;
		this.function = easeType.function;
		
		return this;
	}
	
	public BaseTween start()
	{
		TweenManager.getInstance().add(this);
		
		return this;
	}

	void internalStart(long time)
	{
		started = true;
		startTime = time;
		endTime = time + duration;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		V value = getTweenValue(timestamp);

		onEase(target, value);
		
		completed = timestamp >= endTime;
	}

	protected abstract void onEase(T target, V value);
	
	abstract V getTweenValue(long timestamp);
	
	abstract void setValues(V startValue, V endValue);
	
	@Override
	public final void dispose()
	{
		if (function != null)
		{
			TweenManager.getInstance().remove(this);
			function = null;
			
			onDispose();
		}
	}

	protected void onDispose()
	{
	}
}
