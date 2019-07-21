package zyx.net.data;

import java.util.LinkedList;
import zyx.net.writers.AbstractSerializer;

final class DataEntry
{
	private static final LinkedList<DataEntry> POOL = new LinkedList<>();
	private static int poolCount = 0;
	
	public int size;
	
	String name;
	Object data;
	AbstractSerializer writer;
	
	private DataEntry()
	{
	}
	
	static DataEntry getInstance(String name, Object data, AbstractSerializer writer)
	{
		DataEntry entry = getOrCreateInstance();
		
		entry.name = name;
		entry.writer = writer;
		entry.data = data;
		entry.size = writer.getLength(name, data);
		
		return entry;
	}
	
	void releaseInstance()
	{
		writer = null;
		name = null;
		data = null;
		size = 0;
		
		POOL.addLast(this);
		poolCount++;
	}
	
	private static DataEntry getOrCreateInstance()
	{
		if (poolCount > 0)
		{
			poolCount--;
			return POOL.removeFirst();
		}
		
		return new DataEntry();
	}
}
