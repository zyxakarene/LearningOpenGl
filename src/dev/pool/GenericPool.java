package dev.pool;

import java.util.LinkedList;
import zyx.utils.interfaces.IDisposeable;

public class GenericPool<T extends IPoolable> implements IDisposeable
{

	private final LinkedList<T> pool;
	private final Class<T> typeClass;
	private final Object[] initializeArgs;

	public GenericPool(Class<T> type, int initialSize, Object... arguments)
	{
		pool = new LinkedList<>();
		typeClass = type;
		initializeArgs = arguments;

		PoolAdder.addToPool(pool, typeClass, initialSize, initializeArgs);
	}

	public T getInstance()
	{
		if (pool.isEmpty())
		{
			PoolAdder.addToPool(pool, typeClass, 3, initializeArgs);
		}

		T instance = pool.remove();
		instance.reset();
		
		return instance;
	}

	public void releaseInstance(T instance)
	{
		instance.release();

		pool.add(instance);
	}

	@Override
	public void dispose()
	{
		T instance;
		int len = pool.size();
		for (int i = 0; i < len; i++)
		{
			instance = pool.remove();
			instance.dispose();
		}
		
		len = initializeArgs.length;
		for (int i = 0; i < len; i++)
		{
			initializeArgs[i] = null;
		}
	}

	@Override
	public String toString()
	{
		return String.format("GenericPool<%s> - Size: %s", typeClass, pool.size());
	}
	
	
}
