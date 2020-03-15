package zyx.utils.tween;

import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.tween.easing.EasingFunction;

abstract class AbstractTween<T, V> implements IUpdateable, IDisposeable
{

	AbstractTween()
	{
	}
	
	abstract void internalStart(long time);
	
	public abstract AbstractTween<T, V> setTweenData(T target, V startValue, V endValue, int duration, EasingFunction easeType);
}
