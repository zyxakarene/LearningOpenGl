package zyx.net.writers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ByteSerializer extends AbstractSerializer
{

	ByteSerializer()
	{
	}

	@Override
	protected void onWrite(Object obj, ObjectOutputStream out) throws IOException
	{
		byte value = (byte) obj;
		out.writeByte(value);
	}

	@Override
	public Object read(ObjectInputStream in) throws IOException
	{
		return in.readByte();
	}

	@Override
	public int getDataLength(Object obj)
	{
		return Byte.BYTES;
	}

	@Override
	protected short getType()
	{
		return Serializers.TYPE_BYTE;
	}
}
