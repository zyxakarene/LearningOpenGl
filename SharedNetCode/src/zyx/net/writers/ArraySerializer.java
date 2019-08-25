package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import zyx.net.data.ReadableDataArray;
import zyx.net.data.WriteableDataArray;

class ArraySerializer extends AbstractSerializer
{

	ArraySerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		WriteableDataArray value = (WriteableDataArray) obj;
		byte[] serializedData = value.serialize();
		
		out.writeInt(serializedData.length);
		out.write(serializedData);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		int length = in.readInt();
		byte[] buffer = new byte[length];
		
		in.readFully(buffer);
		ReadableDataArray data = new ReadableDataArray(buffer);
		
		return data;
	}

	@Override
	public int getDataLength(Object obj)
	{
		WriteableDataArray value = (WriteableDataArray) obj;
		return value.length();
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_ARRAY;
	}
}
