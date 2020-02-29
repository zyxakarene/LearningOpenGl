package zyx.game.debug.pools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import zyx.utils.pooling.ObjectPool;

public class DebugPoolList
{

	private static final HashMap<ObjectPool, PoolInfo> POOLS = new HashMap<>();

	private static final HashMap<ObjectPool, Boolean> CHANGED_POOLS = new HashMap<>();

	public static void addPool(ObjectPool pool, int size)
	{
		synchronized (POOLS)
		{
			if (POOLS.containsKey(pool) == false)
			{
				PoolInfo info = new PoolInfo(pool.hashCode(), pool.getPoolType());
				info.setAmount(size, 0, size);
				
				POOLS.put(pool, info);
				CHANGED_POOLS.put(pool, Boolean.TRUE);
			}
		}
	}

	public static void removePool(ObjectPool pool)
	{
		synchronized (POOLS)
		{
			POOLS.remove(pool);
			CHANGED_POOLS.put(pool, Boolean.FALSE);
		}
	}

	public static void updatePoolInfo(ObjectPool pool, int current, int taken, int max)
	{
		synchronized (POOLS)
		{
			PoolInfo poolInfo = POOLS.get(pool);
			if (poolInfo != null)
			{
				poolInfo.setAmount(current, taken, max);
				CHANGED_POOLS.put(pool, Boolean.TRUE);
			}
		}
	}

	public static boolean hasChanges()
	{
		synchronized (POOLS)
		{
			return CHANGED_POOLS.size() > 0;
		}
	}

	public static ArrayList<PoolInfo> getPoolChanges()
	{
		synchronized (POOLS)
		{
			ArrayList<PoolInfo> out = new ArrayList<>();
			
			Set<ObjectPool> keys = CHANGED_POOLS.keySet();
			Iterator<ObjectPool> keyIterator = keys.iterator();
			
			while (keyIterator.hasNext())
			{
				ObjectPool key = keyIterator.next();
				PoolInfo poolInfo = POOLS.get(key);
				out.add(poolInfo);
			}
			
			CHANGED_POOLS.clear();
			
			return out;
		}
	}
}
