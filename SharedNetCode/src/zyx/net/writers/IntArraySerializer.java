package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class IntArraySerializer extends AbstractSerializer
{

	IntArraySerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		int[] value = (int[]) obj;
		
		out.writeInt(value.length);
		for (int arrayValue : value)
		{
			out.writeInt(arrayValue);
		}
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		int len = in.readInt();
		int[] data = new int[len];
		for (int i = 0; i < len; i++)
		{
			data[i] = in.readInt();
		}
		
		return data;
	}

	@Override
	public int getDataLength(Object obj)
	{
		int[] value = (int[]) obj;
		return value.length * Integer.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_INT_ARRAY;
	}
}
