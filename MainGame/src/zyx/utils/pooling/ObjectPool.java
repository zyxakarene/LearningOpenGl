package zyx.utils.pooling;

import zyx.game.debug.pools.DebugPoolList;
import java.util.LinkedList;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;

public class ObjectPool<T> implements IDisposeable
{

	private static final Object[] EMPTY_ARRAY = new Object[0];

	private final LinkedList<T> pool;
	private final Class<? extends T> typeClass;
	private final Object[] initializeArgs;
	private int poolSize;
	private int takenCount;
	private int initSize;

	public ObjectPool(Class<? extends T> type, int initialSize, Object[] args)
	{
		pool = new LinkedList<>();
		typeClass = type;
		initializeArgs = args;

		PoolAdder.addToPool(pool, typeClass, initialSize, initializeArgs);
		poolSize = initialSize;
		initSize = initialSize;
		
		DebugPoolList.addPool(this, poolSize);
	}

	public ObjectPool(Class<? extends T> type, int initialSize)
	{
		this(type, initialSize, EMPTY_ARRAY);
	}

	public synchronized T getInstance()
	{
		if (pool.isEmpty())
		{
			PoolAdder.addToPool(pool, typeClass, 3, initializeArgs);
			poolSize += 3;
		}
		
		T instance = pool.remove();

		takenCount++;
		
		DebugPoolList.updatePoolInfo(this, pool.size(), takenCount, poolSize);
		
		return instance;
	}

	public synchronized void releaseInstance(T instance)
	{
		pool.add(instance);
		takenCount--;
		
		DebugPoolList.updatePoolInfo(this, pool.size(), takenCount, poolSize);
	}

	@Override
	public String toString()
	{
		return String.format("%s<%s> - Size: %s/%s", getClass().getSimpleName(), typeClass, pool.size(), poolSize);
	}

	public String getPoolType()
	{
		return typeClass.getSimpleName();
	}
	
	@Override
	public synchronized void dispose()
	{
		if (poolSize != pool.size())
		{
			Print.out("WARNING: Pool size not right\n", this);
		}
		
		T instance;
		int len = pool.size();
		for (int i = 0; i < len; i++)
		{
			instance = pool.remove();
			if (instance instanceof IDisposeable)
			{
				((IDisposeable) instance).dispose();
			}
		}

		len = initializeArgs.length;
		for (int i = 0; i < len; i++)
		{
			initializeArgs[i] = null;
		}
		
		DebugPoolList.removePool(this);
	}

}
