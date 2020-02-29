package zyx.debug.network.vo.pools;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PoolInformation
{

	private static boolean hasPoolChanges;
	
	private static final ArrayList<PoolInfo> POOL_LIST = new ArrayList<>();
	private static final HashMap<Integer, PoolInfo> POOL_MAP = new HashMap<>();

	public static void fromData(byte[] data) throws IOException
	{
		synchronized (POOL_LIST)
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
			int count = in.readByte();
			for (int i = 0; i < count; i++)
			{
				int id = in.readInt();
				int free = in.readInt();
				int taken = in.readInt();
				int total = in.readInt();

				PoolInfo info = POOL_MAP.get(id);
				if (info == null)
				{
					info = new PoolInfo("", id);
					POOL_MAP.put(id, info);
					POOL_LIST.add(info);
				}

				info.setAmount(free, taken, total);
			}

			hasPoolChanges = true;
		}
	}

	public static boolean hasPoolChanges()
	{
		synchronized (POOL_LIST)
		{
			return hasPoolChanges;
		}
	}
	
	public static ArrayList<PoolInfo> getPoolData()
	{
		synchronized (POOL_LIST)
		{
			ArrayList<PoolInfo> clone = new ArrayList<>(POOL_LIST);
			POOL_LIST.clear();
			
			return clone;
		}
	}
}
