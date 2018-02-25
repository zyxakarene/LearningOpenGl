package zyx.net.data;

import java.io.IOException;
import java.util.ArrayList;

public class ReadableDataArray
{

	private ArrayList<Object> dataList;

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

	public Object get(int index)
	{
		return dataList.get(index);
	}
}
