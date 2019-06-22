package zyx.net.data;

import java.io.IOException;
import java.util.HashMap;

public class ReadableDataObject
{

	private HashMap<String, Object> dataMap;

	public ReadableDataObject(byte[] data)
	{
		dataMap = new HashMap<>();
		try
		{
			new DataObjectDeserializer(data).deserializeMap(dataMap);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Could not deserialize data", ex);
		}
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

	public ByteArrayObject getByteArray(String name)
	{
		return (ByteArrayObject) dataMap.get(name);
	}
}
