package zyx.utils.pooling;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.debug.views.pools.PoolInfo;

public class DebugPoolList
{
	private static final HashMap<ObjectPool, PoolInfo> POOLS = new HashMap<>();
	private static final ArrayList<PoolInfo> POOL_LIST = new ArrayList<>();
	
	private static boolean hasChanges = true;
	
	static void addPool(ObjectPool pool)
	{
		synchronized(POOLS)
		{
			if (POOLS.containsKey(pool) == false)
			{
				PoolInfo info = new PoolInfo(pool.getPoolType());
				POOLS.put(pool, info);
				POOL_LIST.add(info);
			}
		}
	}
	
	static void removePool(ObjectPool pool)
	{
		synchronized(POOLS)
		{
			PoolInfo removed = POOLS.remove(pool);
			if (removed != null)
			{
				POOL_LIST.remove(removed);
			}
		}
	}
	
	static void updatePoolInfo(ObjectPool pool, int current, int taken, int max)
	{
		synchronized(POOLS)
		{
			PoolInfo poolInfo = POOLS.get(pool);
			
			if(poolInfo != null)
			{
				poolInfo.setAmount(current, taken, max);
				hasChanges = true;
			}
		}
	}

	public static boolean hasChanges()
	{
		boolean has = hasChanges;
		hasChanges = false;
		
		return has;
	}
	
	public static void getPoolStatus(ArrayList<PoolInfo> out)
	{
		synchronized(POOLS)
		{
			out.clear();
			out.addAll(POOL_LIST);
		}
	}
}
