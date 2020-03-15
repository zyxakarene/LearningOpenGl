package zyx.utils.tween;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector3f;

public abstract class Vector3fTween<T> extends BaseTween<T, Vector3f>
{
	private static final Vector3f RETURN_VECTOR = new Vector3f();
	private static final ObjectPool<Vector3f> VECTOR_POOL = new GenericPool(PoolableVector3f.class, 10);
	
	private Vector3f startValue;
	private Vector3f endValue;
	
	@Override
	Vector3f getTweenValue(long timestamp)
	{
		RETURN_VECTOR.x = function.ease(startTime, timestamp, startValue.x, endValue.x, duration);
		RETURN_VECTOR.y = function.ease(startTime, timestamp, startValue.y, endValue.y, duration);
		RETURN_VECTOR.z = function.ease(startTime, timestamp, startValue.z, endValue.z, duration);
		
		return RETURN_VECTOR;
	}
	
	@Override
	void setValues(Vector3f startValue, Vector3f endValue)
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
