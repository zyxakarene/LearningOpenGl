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
		return (byte) dataMap.get(name);
	}

	public boolean getBoolean(String name)
	{
		return (boolean) dataMap.get(name);
	}

	public short getShort(String name)
	{
		return (short) dataMap.get(name);
	}

	public char getCharacter(String name)
	{
		return (char) dataMap.get(name);
	}

	public int getInteger(String name)
	{
		return (int) dataMap.get(name);
	}

	public long getLong(String name)
	{
		return (long) dataMap.get(name);
	}

	public float getFloat(String name)
	{
		return (float) dataMap.get(name);
	}

	public double getDouble(String name)
	{
		return (double) dataMap.get(name);
	}

	public String getString(String name)
	{
		return (String) dataMap.get(name);
	}

	public ReadableDataObject getObject(String name)
	{
		return (ReadableDataObject) dataMap.get(name);
	}

	public ReadableDataArray getArray(String name)
	{
		return (ReadableDataArray) dataMap.get(name);
	}

	public ByteArrayObject getByteArray(String name)
	{
		return (ByteArrayObject) dataMap.get(name);
	}
}
