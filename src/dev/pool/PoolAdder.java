package dev.pool;

import java.util.Arrays;
import java.util.LinkedList;

class PoolAdder
{

	static void addToPool(LinkedList pool, Class<? extends IPoolable> type, int amount, Object[] initializeArgs)
	{
		try
		{
			innerInitialize(pool, type, amount, initializeArgs);
		}
		catch (ReflectiveOperationException ex)
		{
			String msg = String.format("Could not create pool of %s with %s args. Size: %s", type, Arrays.toString(initializeArgs), amount);
			throw new RuntimeException(msg, ex);
		}
	}

	private static void innerInitialize(LinkedList pool, Class<? extends IPoolable> type, int initialSize, Object[] initializeArgs) throws ReflectiveOperationException
	{
		IPoolable poolableObject;
		for (int i = 0; i < initialSize; i++)
		{
			poolableObject = type.newInstance();
			poolableObject.initialize(initializeArgs);
			pool.add(poolableObject);
		}
	}
}
