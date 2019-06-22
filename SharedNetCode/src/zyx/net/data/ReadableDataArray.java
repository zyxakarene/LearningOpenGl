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
			new DataObjectDeserializer(data).deserializeArray(dataList);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not deserialize data", ex);
		}
	}

	public T get(int index)
	{
		return dataList.get(index);
	}
}
