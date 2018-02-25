package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class IntegerSerializer extends AbstractSerializer
{

	IntegerSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		int value = (int) obj;
		out.writeInt(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readInt();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Integer.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_INTEGER;
	}
}
