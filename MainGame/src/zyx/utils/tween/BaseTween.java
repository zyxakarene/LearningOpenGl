package zyx.utils.tween;

import zyx.engine.utils.callbacks.ICallback;
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
	
	private ICallback<BaseTween> completedCallback;
	
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
		if (completed && completedCallback != null)
		{
			completedCallback.onCallback(this);
			completedCallback = null;
		}
	}

	public void setCompletedCallback(ICallback<BaseTween> callback)
	{
		completedCallback = callback;
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
			completedCallback = null;
			
			onDispose();
		}
	}

	protected void onDispose()
	{
	}
}
