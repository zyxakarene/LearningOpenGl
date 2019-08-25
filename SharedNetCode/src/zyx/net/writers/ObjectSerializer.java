package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import zyx.net.data.ReadableDataObject;
import zyx.net.data.WriteableDataObject;

class ObjectSerializer extends AbstractSerializer
{

	ObjectSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		WriteableDataObject value = (WriteableDataObject) obj;
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
		ReadableDataObject data = new ReadableDataObject(buffer);
		
		return data;
	}

	@Override
	public int getDataLength(Object obj)
	{
		WriteableDataObject value = (WriteableDataObject) obj;
		return value.getSize();
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_OBJECT;
	}
}
