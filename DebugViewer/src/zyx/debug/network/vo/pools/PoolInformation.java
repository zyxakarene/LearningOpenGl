package zyx.debug.network.vo.pools;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PoolInformation
{

	private static boolean hasPoolChanges;
	
	private static final ArrayList<PoolInfo> CHANGED_POOLS_LIST = new ArrayList<>();
	private static final HashMap<Integer, PoolInfo> ALL_POOLS_MAP = new HashMap<>();

	public static void fromData(byte[] data) throws IOException
	{
		synchronized (CHANGED_POOLS_LIST)
		{
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
			int count = in.readByte();
			for (int i = 0; i < count; i++)
			{
				int id = in.readInt();
				int free = in.readInt();
				int taken = in.readInt();
				int total = in.readInt();

				PoolInfo info = ALL_POOLS_MAP.get(id);
				if (info == null)
				{
					info = new PoolInfo("", id);
					ALL_POOLS_MAP.put(id, info);
					CHANGED_POOLS_LIST.add(info);
				}

				info.setAmount(free, taken, total);
			}

			hasPoolChanges = true;
		}
	}

	public static boolean hasPoolChanges()
	{
		synchronized (CHANGED_POOLS_LIST)
		{
			return hasPoolChanges;
		}
	}
	
	public static ArrayList<PoolInfo> getPoolData()
	{
		synchronized (CHANGED_POOLS_LIST)
		{
			ArrayList<PoolInfo> clone = new ArrayList<>(CHANGED_POOLS_LIST);
			
			return clone;
		}
	}
}
