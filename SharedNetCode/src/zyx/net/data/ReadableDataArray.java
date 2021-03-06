package zyx.net.data;

import java.io.IOException;
import java.util.ArrayList;

public class ReadableDataArray<T>
{

	private ArrayList<T> dataList;

	public ReadableDataArray(byte[] data)
	{
		dataList = new ArrayList<>();
		try
		{
			DataObjectDeserializer.deserializeToArray(data, dataList);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not deserialize data", ex);
		}
	}

	public int size()
	{
		return dataList.size();
	}
	
	public T get(int index)
	{
		return dataList.get(index);
	}
}
