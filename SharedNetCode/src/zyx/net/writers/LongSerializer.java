package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class LongSerializer extends AbstractSerializer
{

	LongSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		long value = (long) obj;
		out.writeLong(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readLong();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Long.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_LONG;
	}
}
