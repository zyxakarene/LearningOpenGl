package zyx.net.data;

import java.io.IOException;
import java.util.ArrayList;
import zyx.net.writers.AbstractSerializer;
import zyx.net.writers.Serializers;

public class WriteableDataArray<T> implements IWriteableObject
{

	private static final String EMMPTY_STRING = "";
	
	private ArrayList<DataEntry> dataList;
	private AbstractSerializer serializer;

	public WriteableDataArray(Class<T> clazz)
	{
		this.dataList = new ArrayList<>();
		this.serializer = Serializers.getInstance().GetFromClass(clazz);
	}

	public void add(T value)
	{
		DataEntry entry = DataEntry.getInstance(EMMPTY_STRING, value, serializer);
		dataList.add(entry);
	}

	public T remove(int index)
	{
		return (T) dataList.remove(index).data;
	}

	public int length()
	{
		return dataList.size();
	}

	@Override
	public byte[] serialize()
	{
		try
		{
			DataEntry[] entries = new DataEntry[length()];
			dataList.toArray(entries);
			
			int size = getSize();
			return new DataObjectSerializer(size).toByteArray(entries);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not serialize object", ex);
		}
	}

	@Override
	public int getSize()
	{
		int size = 0;

		for (DataEntry entry : dataList)
		{
			size += serializer.getLength(entry.data);
		}

		return size;
	}

	void dispose()
	{
		for (DataEntry entry : dataList)
		{
			entry.releaseInstance();
		}
		
		dataList.clear();
		dataList = null;
	}
}
