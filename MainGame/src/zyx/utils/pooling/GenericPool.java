package zyx.utils.pooling;

import zyx.utils.interfaces.IDisposeable;

public class GenericPool<T extends IPoolable> extends ObjectPool<T> implements IDisposeable
{

	public GenericPool(Class<? extends T> type, int initialSize, Object... arguments)
	{
		super(type, initialSize, arguments);
	}

	@Override
	public synchronized T getInstance()
	{
		T instance = super.getInstance();
		instance.reset();
		
		return instance;
	}

	@Override
	public synchronized void releaseInstance(T instance)
	{
		instance.release();
		
		super.releaseInstance(instance);
	}
}
