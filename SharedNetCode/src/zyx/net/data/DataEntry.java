package zyx.net.data;

import zyx.net.writers.AbstractSerializer;

final class DataEntry
{

	public int size;
	
	String name;
	Object data;
	AbstractSerializer writer;
	
	DataEntry(String name, Object data, AbstractSerializer writer)
	{
		this.name = name;
		this.writer = writer;
		this.data = data;
		this.size = writer.getLength(name, data);
	}
}
