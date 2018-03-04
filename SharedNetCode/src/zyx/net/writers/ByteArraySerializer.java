package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import zyx.net.data.ByteArrayObject;

class ByteArraySerializer extends AbstractSerializer
{

	ByteArraySerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		ByteArrayObject value = (ByteArrayObject) obj;
		
		out.writeInt(value.data.length);
		out.write(value.data);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		int len = in.readInt();
		byte[] data = new byte[len];
		in.read(data);
		
		return data;
	}

	@Override
	public int getDataLength(Object obj)
	{
		ByteArrayObject value = (ByteArrayObject) obj;
		return value.data.length;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_BYTE_ARRAY;
	}
}
