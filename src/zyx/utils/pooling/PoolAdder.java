package zyx.utils.pooling;

import java.util.Arrays;
import java.util.LinkedList;

class PoolAdder
{

	static void addToPool(LinkedList pool, Class type, int amount, Object[] initializeArgs)
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

	private static void innerInitialize(LinkedList pool, Class type, int initialSize, Object[] initializeArgs) throws ReflectiveOperationException
	{
		Object poolableObject;
		for (int i = 0; i < initialSize; i++)
		{
			poolableObject = type.newInstance();
			if (poolableObject instanceof IPoolable)
			{
				((IPoolable) poolableObject).initialize(initializeArgs);
			}
			pool.add(poolableObject);
		}
	}
}
