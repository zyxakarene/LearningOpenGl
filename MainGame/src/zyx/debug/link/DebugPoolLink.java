package zyx.debug.link;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.debug.views.pools.PoolInfo;
import zyx.utils.pooling.ObjectPool;

public class DebugPoolLink
{
	private final HashMap<ObjectPool, PoolInfo> POOLS = new HashMap<>();
	private final ArrayList<PoolInfo> POOL_LIST = new ArrayList<>();
	
	private boolean hasChanges = true;

	DebugPoolLink()
	{
	}
	
	public void addPool(ObjectPool pool)
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
	
	public void removePool(ObjectPool pool)
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
	
	public void updatePoolInfo(ObjectPool pool, int current, int taken, int max)
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

	public boolean hasChanges()
	{
		boolean has = hasChanges;
		hasChanges = false;
		
		return has;
	}
	
	public void getPoolStatus(ArrayList<PoolInfo> out)
	{
		synchronized(POOLS)
		{
			out.clear();
			out.addAll(POOL_LIST);
		}
	}
}
