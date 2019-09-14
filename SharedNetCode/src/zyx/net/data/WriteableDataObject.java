package zyx.net.data;

import java.util.HashMap;
import java.util.Map;
import zyx.net.writers.Serializers;

public class WriteableDataObject implements IWriteableObject
{

	private HashMap<String, DataEntry> dataMap;
	private Serializers serializers;

	private int cachedSize;

	public WriteableDataObject()
	{
		dataMap = new HashMap<>();
		serializers = Serializers.getInstance();
	}

	public void addByte(String name, byte value)
	{
		addInner(name, value, Serializers.TYPE_BYTE);
	}

	public void addBoolean(String name, boolean value)
	{
		addInner(name, value, Serializers.TYPE_BOOLEAN);
	}

	public void addShort(String name, short value)
	{
		addInner(name, value, Serializers.TYPE_SHORT);
	}

	public void addCharacter(String name, char value)
	{
		addInner(name, value, Serializers.TYPE_CHARACTER);
	}

	public void addFloat(String name, float value)
	{
		addInner(name, value, Serializers.TYPE_FLOAT);
	}

	public void addInteger(String name, int value)
	{
		addInner(name, value, Serializers.TYPE_INTEGER);
	}

	public void addIntegerArray(String name, int[] value)
	{
		addInner(name, value, Serializers.TYPE_INT_ARRAY);
	}

	public void addLong(String name, long value)
	{
		addInner(name, value, Serializers.TYPE_LONG);
	}

	public void addString(String name, String value)
	{
		addInner(name, value, Serializers.TYPE_STRING);
	}

	public void addObject(String name, WriteableDataObject value)
	{
		addInner(name, value, Serializers.TYPE_OBJECT);
	}

	public void addArray(String name, WriteableDataArray value)
	{
		addInner(name, value, Serializers.TYPE_ARRAY);
	}

	public void addByteArray(String name, byte[] value)
	{
		ByteArrayObject wrapper = new ByteArrayObject(value);
		addInner(name, wrapper, Serializers.TYPE_BYTE_ARRAY);
	}

	private void addInner(String name, Object value, short type)
	{
		DataEntry entry = DataEntry.getInstance(name, value, serializers.getFromType(type));
		dataMap.put(name, entry);

		cachedSize = -1;
	}

	@Override
	public int getSize()
	{
		if (cachedSize < 0)
		{
			cachedSize = 0;
			for (Map.Entry<String, DataEntry> entry : dataMap.entrySet())
			{
				DataEntry value = entry.getValue();
				cachedSize += value.size;
			}
		}

		return cachedSize;
	}

	@Override
	public byte[] serialize()
	{
		try
		{
			int counter = 0;
			DataEntry[] entries = new DataEntry[dataMap.size()];
			for (Map.Entry<String, DataEntry> entry : dataMap.entrySet())
			{
				DataEntry value = entry.getValue();
				entries[counter++] = value;
			}

			int size = getSize();
			return new DataObjectSerializer(size).toByteArray(entries);
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Could not serialize object", ex);
		}
	}

	public void dispose()
	{
		for (Map.Entry<String, DataEntry> entry : dataMap.entrySet())
		{
			DataEntry value = entry.getValue();

			if (value.data instanceof WriteableDataArray)
			{
				((WriteableDataArray) value.data).dispose();
			}

			value.releaseInstance();
		}
		
		dataMap.clear();
		dataMap = null;
	}
}
