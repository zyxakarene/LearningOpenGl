package zyx.net.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadableDataObject
{

	private HashMap<String, Object> dataMap;

	public ReadableDataObject(byte[] data)
	{
		dataMap = new HashMap<>();
		try
		{
			DataObjectDeserializer.deserializeToMap(data, dataMap);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not deserialize data", ex);
		}
	}

	public String[] getAllKeys()
	{
		String[] keys = new String[dataMap.size()];

		int i = 0;
		for (Map.Entry<String, Object> entry : dataMap.entrySet())
		{
			keys[i] = entry.getKey();
			i++;
		}

		return keys;
	}

	public String getToString(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? "null" : value.toString();
	}

	public Object getRaw(String name)
	{
		return dataMap.get(name);
	}

	public byte getByte(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0 : (byte) value;
	}

	public boolean getBoolean(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? false : (boolean) value;
	}

	public short getShort(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0 : (short) value;
	}

	public char getCharacter(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? null : (char) value;
	}

	public int getInteger(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0 : (int) value;
	}

	public long getLong(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0L : (long) value;
	}

	public float getFloat(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0f : (float) value;
	}

	public double getDouble(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? 0d : (double) value;
	}

	public String getString(String name)
	{
		Object value = dataMap.get(name);
		return value == null ? null : (String) value;
	}

	public ReadableDataObject getObject(String name)
	{
		return (ReadableDataObject) dataMap.get(name);
	}

	public <T> ReadableDataArray<T> getArray(String name)
	{
		return (ReadableDataArray<T>) dataMap.get(name);
	}

	public byte[] getByteArray(String name)
	{
		return (byte[]) dataMap.get(name);
	}
}
