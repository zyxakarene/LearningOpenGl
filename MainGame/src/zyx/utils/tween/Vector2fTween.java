package zyx.utils.tween;

import org.lwjgl.util.vector.Vector2f;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector2f;

public abstract class Vector2fTween<T> extends BaseTween<T, Vector2f>
{
	private static final Vector2f RETURN_VECTOR = new Vector2f();
	
	private static final ObjectPool<Vector2f> VECTOR_POOL = new GenericPool(PoolableVector2f.class, 10);
	
	private Vector2f startValue;
	private Vector2f endValue;
	
	@Override
	Vector2f getTweenValue(long timestamp)
	{
		RETURN_VECTOR.x = function.ease(startTime, timestamp, startValue.x, endValue.x, duration);
		RETURN_VECTOR.y = function.ease(startTime, timestamp, startValue.y, endValue.y, duration);
		
		return RETURN_VECTOR;
	}

	@Override
	void setValues(Vector2f startValue, Vector2f endValue)
	{
		this.startValue = VECTOR_POOL.getInstance();
		this.endValue = VECTOR_POOL.getInstance();
		
		this.startValue.set(startValue);
		this.endValue.set(endValue);
	}

	@Override
	protected void onDispose()
	{
		VECTOR_POOL.releaseInstance(startValue);
		VECTOR_POOL.releaseInstance(endValue);
		
		startValue = null;
		endValue = null;
	}
}
